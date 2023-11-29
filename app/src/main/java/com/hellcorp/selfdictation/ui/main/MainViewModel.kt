package com.hellcorp.selfdictation.ui.main

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hellcorp.selfdictation.domain.TextSetInteractor
import com.hellcorp.selfdictation.domain.models.SetListState
import com.hellcorp.selfdictation.utils.BaseViewModel

class MainViewModel(private val interactor: TextSetInteractor) : BaseViewModel() {
    private val _state = MutableLiveData<SetListState>()
    val state: LiveData<SetListState>
        get() = _state

    private val _time = MutableLiveData<String>()
    val time: LiveData<String>
        get() = _time

    private var timer: CountDownTimer? = null
    private var elapsedTime = 0L

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }

    fun countLetters(str: String): Int {
        return str.count { it.isLetter() }
    }

    fun startTimer() {
        timer?.cancel()
        elapsedTime = 0L

        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                elapsedTime += 1000
                Log.d("MainViewModelMyLog", "Timer tick: $elapsedTime")
                val minutes = (elapsedTime / 1000) / 60
                val seconds = (elapsedTime / 1000) % 60
                _time.postValue(String.format("%02d:%02d", minutes, seconds))
            }

            override fun onFinish() {
            }
        }.start()
    }

    fun stopTimer() {
        timer?.cancel()
    }
}
