package com.hellcorp.selfdictation.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hellcorp.selfdictation.db.dao.LinesDao
import com.hellcorp.selfdictation.db.dao.TextSetDao
import com.hellcorp.selfdictation.db.dao.TextSetLinesDao
import com.hellcorp.selfdictation.db.entity.LineEntity
import com.hellcorp.selfdictation.db.entity.TextSetEntity
import com.hellcorp.selfdictation.db.entity.TextSetLinesEntity

@Database(
    version = 1,
    entities = [LineEntity::class, TextSetEntity::class, TextSetLinesEntity::class],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun linesDao(): LinesDao
    abstract fun textSetDao(): TextSetDao
    abstract fun textSetLinesDao(): TextSetLinesDao
}