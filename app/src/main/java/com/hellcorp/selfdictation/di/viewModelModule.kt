package com.hellcorp.selfdictation.di

import com.hellcorp.selfdictation.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
}
