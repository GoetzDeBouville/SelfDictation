package com.hellcorp.selfdictation.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lines_table")
data class LineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val number: Int,
    val line: String,
    val letersNum: Int,
    val timeSec: Int
)
