package com.hellcorp.selfdictation.ui.main.viewmodels

import android.util.Log
import android.util.Pair
import androidx.lifecycle.viewModelScope
import com.hellcorp.selfdictation.domain.TextSetInteractor
import com.hellcorp.selfdictation.domain.models.Line
import com.hellcorp.selfdictation.domain.models.SetListState
import com.hellcorp.selfdictation.domain.models.TextSet
import com.hellcorp.selfdictation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

typealias PairTextSet = Pair<TextSet, List<Line>>

class UsersSetViewmodel(val interactor: TextSetInteractor) : BaseViewModel() {
    private val pairList: MutableList<PairTextSet> = mutableListOf()
    private var _state = MutableStateFlow<SetListState>(SetListState.Loading)
    val state: StateFlow<SetListState>
        get() = _state

    fun loadDataFromDB() {
        viewModelScope.launch {
            interactor.getSetList().catch {
                Log.e("UsersSetViewmodel", "${it.cause} : ${it.message} \n${it.localizedMessage}")
            }.collect { result ->
                processResult(result)
            }
        }
    }

    private fun processResult(list: List<TextSet>) {
        if (list.isEmpty()) {
            _state.value = SetListState.Empty
        } else {
            viewModelScope.launch {
                list.forEach { set ->
                    val lines = interactor.getLineList(set.id).firstOrNull() ?: emptyList()
                    val pair = PairTextSet(set, lines)
                    pairList.add(pair)
                }
                _state.value = SetListState.Content(pairList)
            }
        }
    }

    fun addNewSet(pairTextSet: PairTextSet) {
        viewModelScope.launch {
            interactor.addNewSet(pairTextSet.first)
            pairTextSet.second.forEach {
                interactor.addLineToSet(pairTextSet.first, it)
            }
            pairList.add(pairTextSet)
            _state.value = SetListState.Content(pairList)
        }
    }

    fun removeSet(pairTextSet: PairTextSet) {
        viewModelScope.launch {
            pairList.remove(pairTextSet)
            interactor.getLineList(pairTextSet.first.id).collect { list ->
                list.forEach { line ->
                    interactor.removeLine(line.id)
                }
            }
            interactor.removeSet(pairTextSet.first.id)
            if (pairList.isEmpty()) {
                _state.value = SetListState.Empty
            } else {
                _state.value = SetListState.Content(pairList)
            }
        }
    }

    fun updateSetList(pairTextSet: PairTextSet) {
        viewModelScope.launch {
            interactor.updateSet(pairTextSet.first)
        }
    }
}
