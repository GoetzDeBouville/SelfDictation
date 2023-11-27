package com.hellcorp.selfdictation.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hellcorp.selfdictation.db.dao.LinesDao
import com.hellcorp.selfdictation.db.dao.TextSetDao
import com.hellcorp.selfdictation.db.dao.TextSetLinesDao
import com.hellcorp.selfdictation.db.entity.LineEntity
import com.hellcorp.selfdictation.db.entity.TextSetEntity
import com.hellcorp.selfdictation.db.entity.TextSetLinesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Database(
    version = 1,
    entities = [LineEntity::class, TextSetEntity::class, TextSetLinesEntity::class],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun linesDao(): LinesDao
    abstract fun textSetDao(): TextSetDao
    abstract fun textSetLinesDao(): TextSetLinesDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "app_database"
//                )
//                    .fallbackToDestructiveMigration()
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//
//
//        suspend fun prepopulateDatabase(context: Context) {
//            val database = getDatabase(context)
//            if (!isDatabasePopulated(context)) {
//                val lines = listOf(
//                    LineEntity(1, 1, "Тает снег.", 8, 4),
//                    LineEntity(2, 2, "Идёт дождь.", 9, 4),
//                    LineEntity(3, 3, "Небо хмурое.", 10, 5),
//                    LineEntity(4, 4, "Коля заболел.", 11, 5),
//                    LineEntity(5, 5, "Запели птицы.", 11, 5),
//                    LineEntity(6, 6, "Поле опустело.", 12, 6)
//                )
//                val textSet = TextSetEntity(1, "YourTextSetName")
//                withContext(Dispatchers.IO) {
//                    database.runInTransaction {
//                        lines.forEach { line -> database.linesDao().insertLine(line) }
//                        database.textSetDao().insertSet(textSet)
//                        val textSetLines = lines.map { TextSetLinesEntity(textSet.id, it.id) }
//                        textSetLines.forEach { database.textSetLinesDao().insertLinesSet(it) }
//                    }
//                }
//                setDatabasePopulated(context)
//            }
//        }
//
//        private const val PREFS_NAME = "preference_file_key"
//        private const val DATABASE_POPULATED_KEY = "database_populated_key"
//
//        private fun isDatabasePopulated(context: Context): Boolean {
//            val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//            return sharedPrefs.getBoolean(DATABASE_POPULATED_KEY, false)
//        }
//
//        private fun setDatabasePopulated(context: Context) {
//            val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//            sharedPrefs.edit().putBoolean(DATABASE_POPULATED_KEY, true).apply()
//        }
//    }
}