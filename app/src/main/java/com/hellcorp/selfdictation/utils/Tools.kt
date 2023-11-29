package com.hellcorp.selfdictation.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.hellcorp.selfdictation.R


object Tools {
    const val LIST_LINES = "list_lines"

    fun amountTextFormater(amount: Int): String {
        val lastDigit = amount % 10
        val lastTwoDigits = amount % 100

        return when {
            lastTwoDigits in 11..14 -> "$amount треков"
            lastDigit == 1 -> "$amount трек"
            lastDigit in 2..4 -> "$amount трека"
            else -> "$amount треков"
        }
    }

    fun isBackgroundColorLight(color: Int): Boolean {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val luminance = (0.2126 * red + 0.7152 * green + 0.0722 * blue).toFloat()
        return luminance > 160
    }

    fun showSnackbar(
        view: View,
        message: String,
        context: Context
    ) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        val snackTextColor = ContextCompat.getColor(context, R.color.white)
        val backgroundColor = ContextCompat.getColor(context, R.color.black)

        val textView =
            snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.textSize = 16f
        textView.setTextColor(snackTextColor)
        snackbar.view.setBackgroundColor(backgroundColor)
        snackbar.show()
    }
}
