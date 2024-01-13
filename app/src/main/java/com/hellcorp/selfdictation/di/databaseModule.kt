package com.hellcorp.selfdictation.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hellcorp.selfdictation.db.AppDatabase
import com.hellcorp.selfdictation.db.entity.LineEntity
import com.hellcorp.selfdictation.db.entity.TextSetEntity
import com.hellcorp.selfdictation.db.entity.TextSetLinesEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app_database.db")
            .build()
    }
}
