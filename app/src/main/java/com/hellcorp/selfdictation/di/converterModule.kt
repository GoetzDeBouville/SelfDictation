package com.hellcorp.selfdictation.di

import com.hellcorp.selfdictation.data.converters.LinesDbConverter
import com.hellcorp.selfdictation.data.converters.TextSetDbConverter
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val converterModule = module {
    factoryOf(::LinesDbConverter)
    factoryOf(::TextSetDbConverter)
}
