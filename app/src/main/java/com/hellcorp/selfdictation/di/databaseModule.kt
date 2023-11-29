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
        val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    fillDatabase(get())
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
            }
        }

        // create and configure DB
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database.db"
        )
            .addCallback(roomCallback) // add Callback
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().linesDao() }
    single { get<AppDatabase>().textSetDao() }
    single { get<AppDatabase>().textSetLinesDao() }
}

suspend fun fillDatabase(database: AppDatabase) {
    val textSetEntities = listOf(
        TextSetEntity(id = 1, name = "Set 1"),
        TextSetEntity(id = 2, name = "Set 2"),
        TextSetEntity(id = 3, name = "Set 3")
    )

    val lineEntities = listOf(
        LineEntity(id = 1, number = 1, line = "Тает снег.", letersNum = 8, timeSec = 4),
        LineEntity(id = 2, number = 2, line = "Идёт дождь.", letersNum = 9, timeSec = 4),
        LineEntity(id = 3, number = 3, line = "Небо хмурое.", letersNum = 10, timeSec = 5),
        LineEntity(id = 4, number = 4, line = "Коля заболел.", letersNum = 11, timeSec = 5),
        LineEntity(id = 5, number = 5, line = "Запели птицы.", letersNum = 11, timeSec = 5),
        LineEntity(id = 6, number = 6, line = "Поле опустело.", letersNum = 12, timeSec = 6),
    )

    val textSetlineEntities = listOf(
        TextSetLinesEntity(textSetId = 1, linesId = 1),
        TextSetLinesEntity(textSetId = 1, linesId = 2),
        TextSetLinesEntity(textSetId = 1, linesId = 3),
        TextSetLinesEntity(textSetId = 1, linesId = 4),
        TextSetLinesEntity(textSetId = 1, linesId = 5),
        TextSetLinesEntity(textSetId = 1, linesId = 6),
    )

    database.textSetDao().insertSets(textSetEntities)
    database.linesDao().insertLines(lineEntities)
    database.textSetLinesDao().insertLinesSets(textSetlineEntities)
}
