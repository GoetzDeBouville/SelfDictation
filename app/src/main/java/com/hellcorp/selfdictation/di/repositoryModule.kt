package com.hellcorp.selfdictation.di

import com.hellcorp.selfdictation.data.db.TextSetRepositoryImpl
import com.hellcorp.selfdictation.domain.TextSetRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::TextSetRepositoryImpl) { bind<TextSetRepository>() }
}
