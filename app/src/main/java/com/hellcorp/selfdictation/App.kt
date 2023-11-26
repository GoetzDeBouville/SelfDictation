package com.hellcorp.selfdictation

import android.app.Application

class App : Application() {

    private val MAX_LAUNCH_COUNT = 150

    override fun onCreate() {
        super.onCreate()
        if (checkLaunchLimitsExceeded()) {
            throw RuntimeException("Превышен лимит запусков приложения.")
        }
        updateLaunchCount()
    }

    private fun updateLaunchCount() {
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val currentCount = prefs.getInt("launch_count", 0)
        prefs.edit().putInt("launch_count", currentCount + 1).apply()
    }

    private fun checkLaunchLimitsExceeded(): Boolean {
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val currentCount = prefs.getInt("launch_count", 0)
        return currentCount >= MAX_LAUNCH_COUNT
    }
}
