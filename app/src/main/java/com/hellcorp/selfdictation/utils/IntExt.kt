package com.hellcorp.selfdictation.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.hellcorp.selfdictation.R
import java.util.Locale

fun Int.getquantityString(context: Context): String {
    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(Locale("ru"))
    val localizedResources: Resources = context.createConfigurationContext(configuration).resources
    return localizedResources.getQuantityString(
        R.plurals.founded_sets,
        this,
        this
    )
}
