package com.hellcorp.selfdictation.di

import com.hellcorp.selfdictation.domain.TextSetInteractor
import com.hellcorp.selfdictation.domain.impl.TextSetInteractorImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val interactorModule = module {
    singleOf(::TextSetInteractorImpl) { bind<TextSetInteractor>() }
}
