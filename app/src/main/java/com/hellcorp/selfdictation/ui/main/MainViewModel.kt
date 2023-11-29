package com.hellcorp.selfdictation.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hellcorp.selfdictation.domain.TextSetInteractor
import com.hellcorp.selfdictation.domain.models.SetListState
import com.hellcorp.selfdictation.utils.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val interactor: TextSetInteractor) : BaseViewModel() {
    private val _state = MutableLiveData<SetListState>()
    val state: LiveData<SetListState>
        get() = _state

    fun getSetList() {
        viewModelScope.launch {
            try {
                interactor.getSetList().collect {
                    if (it.isEmpty()) _state.postValue(SetListState.Empty)
                    else _state.postValue(SetListState.Content(it))
                }
            } catch (e: Exception) {
                Log.e("Coroutine Exception", e.stackTraceToString())
            }
        }
    }

    fun countLetters(str: String): Int {
        return str.count { it.isLetter() }
    }


}
