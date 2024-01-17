package com.hellcorp.selfdictation.ui.newcard

import android.util.Log
import android.util.Pair
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.textfield.TextInputEditText
import com.hellcorp.selfdictation.domain.TextSetInteractor
import com.hellcorp.selfdictation.domain.models.Line
import com.hellcorp.selfdictation.domain.models.PairTextSet
import com.hellcorp.selfdictation.domain.models.TextSet
import com.hellcorp.selfdictation.utils.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

typealias PairTextSet = Pair<TextSet, List<Line>>

class NewCardViewModel(private val interactor: TextSetInteractor) : BaseViewModel() {
    val cardData: PairTextSet? = null
    private var _stateFields = MutableLiveData<Boolean>()
    val stateFields: LiveData<Boolean>
        get() = _stateFields

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
            val setId = saveSetToDB(pairTextSet.first)
            pairTextSet.second.forEach {
                saveLinetoDB(setId, it)
            }
        }
    }

    private suspend fun saveSetToDB(textSet: TextSet): Int = suspendCoroutine { continuation ->
        var setId = 0
        viewModelScope.launch {
            interactor.addNewSet(textSet)
            interactor.getLastIdSet().collect {
                setId = it
                continuation.resume(setId)
            }
        }
    }

    private fun saveLinetoDB(setId: Int, line: Line) {
        viewModelScope.launch {
            interactor.addLineToSet(setId, line)
        }
    }

    fun updateStateFields(value: Boolean) {
        _stateFields.value = value
    }
}