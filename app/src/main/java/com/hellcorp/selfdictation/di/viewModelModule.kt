package com.hellcorp.selfdictation.di

import com.hellcorp.selfdictation.ui.card.viewmodel.CardViewModel
import com.hellcorp.selfdictation.ui.main.viewmodels.MainViewModel
import com.hellcorp.selfdictation.ui.usersetlist.UsersSetViewmodel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::UsersSetViewmodel)
    viewModelOf(::CardViewModel)
}
