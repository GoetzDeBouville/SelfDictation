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
import kotlinx.coroutines.launch

typealias PairTextSet = Pair<TextSet, List<Line>>

class NewCardViewModel(val interactor: TextSetInteractor): BaseViewModel() {
    val cardData : PairTextSet? = null
    private var _stateFields = MutableLiveData<Boolean>()
    val stateFields : LiveData<Boolean>
        get() = _stateFields

    fun countSymbols(str: CharSequence?, editText: TextInputEditText) {
        if (!str.isNullOrEmpty()) {
            val numberOfSymbols = str.count { it.isLetter() || it.isDigit() }
            editText.setText(numberOfSymbols.toString())
        } else {
            editText.setText("")
        }
    }

    fun saveDataToDB(pairTextSet: PairTextSet) {
        viewModelScope.launch {
            interactor.addNewSet(pairTextSet.first)
            pairTextSet.second.forEach {
                interactor.addLineToSet(pairTextSet.first, it)
            }
        }
    }

    fun updateStateFields(value: Boolean) {
        Log.i("MyLog", "stateFields in updateStateFields: $value")
        _stateFields.value = value
    }
}