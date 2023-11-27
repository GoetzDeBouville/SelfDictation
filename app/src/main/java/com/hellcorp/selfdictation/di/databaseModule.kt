package com.hellcorp.selfdictation.di

import androidx.room.Room
import com.hellcorp.selfdictation.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "your_database_name" // Замените на фактическое имя вашей базы данных
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().linesDao() }
    single { get<AppDatabase>().textSetDao() }
    single { get<AppDatabase>().textSetLinesDao() }
}
