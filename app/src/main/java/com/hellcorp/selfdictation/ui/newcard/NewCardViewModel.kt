package com.hellcorp.selfdictation.ui.newcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.textfield.TextInputEditText
import com.hellcorp.selfdictation.domain.TextSetInteractor
import com.hellcorp.selfdictation.domain.models.Line
import com.hellcorp.selfdictation.domain.models.TextSet
import com.hellcorp.selfdictation.ui.main.viewmodels.PairTextSet
import com.hellcorp.selfdictation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class NewCardViewModel(private val interactor: TextSetInteractor) : BaseViewModel() {
    val cardData: PairTextSet? = null
    private var _stateFields = MutableLiveData<Boolean>()
    val stateFields: LiveData<Boolean>
        get() = _stateFields

    private var _stateData = MutableStateFlow<CardState>(CardState.Empty)
    val stateData : StateFlow<CardState>
        get() = _stateData

    fun countSymbolsAndSetToEditText(str: CharSequence?, editText: TextInputEditText) {
        if (!str.isNullOrEmpty()) {
            val numberOfSymbols = str.count { it.isLetter() || it.isDigit() }
            editText.setText(numberOfSymbols.toString())
        } else {
            editText.setText("")
        }
    }

    fun countLetters(str: CharSequence): Int {
        return str.count { it.isLetter() || it.isDigit() }
    }

    fun saveDataToDB(pairTextSet: PairTextSet) {
        viewModelScope.launch {
            var setId = 0
            interactor.addNewSet(pairTextSet.first)
            if (pairTextSet.first.id != 0) {
                setId = pairTextSet.first.id
            } else {
                interactor.getLastIdSet().collect {
                    setId = it
                }
            }
            pairTextSet.second.forEach {
                interactor.addLineToSet(setId, it)
            }
        }
    }

    fun updateStateFields(value: Boolean) {
        _stateFields.value = value
    }

    fun getData(setId : Int) {
        viewModelScope.launch {
            interactor.getCardById(setId).collect {
                _stateData.value = CardState.Content(it)
            }
        }
    }
}