package com.hellcorp.selfdictation.ui.main

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hellcorp.selfdictation.utils.BaseViewModel

class MainViewModel() : BaseViewModel() {
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
                val minutes = (elapsedTime / 1000) / 60
                val seconds = (elapsedTime / 1000) % 60
                _time.postValue(String.format("%02d:%02d", minutes, seconds))
            }

            override fun onFinish() {
            }
        }.start()
    }

    fun extractNumber(str: String): String? {
        val numberRegex = "\\d+".toRegex()
        val matchResult = numberRegex.find(str)
        return matchResult?.value
    }

    fun stopTimer() {
        timer?.cancel()
    }
}
