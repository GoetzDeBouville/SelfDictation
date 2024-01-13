package com.hellcorp.selfdictation.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "text_set")
data class TextSetEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)