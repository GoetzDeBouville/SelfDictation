package com.hellcorp.selfdictation

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.hellcorp.selfdictation.databinding.ActivityMainBinding
import com.hellcorp.selfdictation.utils.Tools

class RootActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        val navController = navHostFragment.navController

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.main_background)
        if (Tools.isBackgroundColorLight(ContextCompat.getColor(this, R.color.main_background))) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
            else window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}