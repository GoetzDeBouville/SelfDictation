package com.hellcorp.selfdictation

import android.app.Application
import android.util.Log
import com.hellcorp.selfdictation.di.converterModule
import com.hellcorp.selfdictation.di.databaseModule
import com.hellcorp.selfdictation.di.interactorModule
import com.hellcorp.selfdictation.di.repositoryModule
import com.hellcorp.selfdictation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    private val MAX_LAUNCH_COUNT = 10

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    converterModule,
                    databaseModule,
                    repositoryModule,
                    interactorModule,
                    viewModelModule
                )
            )
        }
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
        Log.i("AppMyLog", "currentCount = $currentCount")
        return currentCount >= MAX_LAUNCH_COUNT
    }
}
