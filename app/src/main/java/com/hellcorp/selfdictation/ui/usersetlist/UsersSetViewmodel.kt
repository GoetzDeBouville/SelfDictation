package com.hellcorp.selfdictation.ui.usersetlist

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.hellcorp.selfdictation.domain.TextSetInteractor
import com.hellcorp.selfdictation.domain.models.SetListState
import com.hellcorp.selfdictation.domain.models.TextSet
import com.hellcorp.selfdictation.ui.main.viewmodels.PairTextSet
import com.hellcorp.selfdictation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class UsersSetViewmodel(private val interactor: TextSetInteractor) : BaseViewModel() {
    private var pairList: MutableList<PairTextSet> = mutableListOf()
    private var _state = MutableStateFlow<SetListState>(SetListState.Loading)
    val state: StateFlow<SetListState>
        get() = _state

    fun loadDataFromDB() {
        viewModelScope.launch {
            pairList.clear()
            interactor.getSetList().catch {
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
                val pairs = list.map { set ->
                    val lines = interactor.getLineList(set.id).firstOrNull() ?: emptyList()
                    PairTextSet(set, lines)
                }
                pairList = pairs.toMutableList()
                _state.value = SetListState.Content(pairList, pairList.size)
            }
        }
    }

    fun addNewSet(pairTextSet: PairTextSet) {
        viewModelScope.launch {
            interactor.addNewSet(pairTextSet.first)
            pairTextSet.second.forEach {
                interactor.addLineToSet(pairTextSet.first.id, it)
            }
            pairList.add(pairTextSet)
            _state.value = SetListState.Content(pairList, pairList.size)
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
                _state.value = SetListState.Content(pairList, pairList.size)
            }
        }
    }

    fun updateSetList(setId: Int) {
        viewModelScope.launch {
            interactor.updateSet(setId)
        }
    }

    fun filterSetList(classNumber: Int) {
        val filteredList = pairList.filter {
            it.first.classNumber == classNumber
        }
        _state.value = SetListState.Content(filteredList, filteredList.size)
    }

    fun buildDataForBundle(pairTextSet: PairTextSet) = arrayOf(
        pairTextSet.first.name,
        pairTextSet.second[0].line,
        pairTextSet.second[1].line,
        pairTextSet.second[2].line,
        pairTextSet.second[3].line,
        pairTextSet.second[4].line,
        pairTextSet.second[5].line,
        pairTextSet.second[0].timeSec.toString(),
        pairTextSet.second[1].timeSec.toString(),
        pairTextSet.second[2].timeSec.toString(),
        pairTextSet.second[3].timeSec.toString(),
        pairTextSet.second[4].timeSec.toString(),
        pairTextSet.second[5].timeSec.toString(),
    )

    fun dpToPx(dp: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }
}