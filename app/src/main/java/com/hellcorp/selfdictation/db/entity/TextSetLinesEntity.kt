package com.hellcorp.selfdictation.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "text_set_lines",
    primaryKeys = ["textSetId", "linesId"],
    foreignKeys = [
        ForeignKey(
            entity = TextSetEntity::class,
            parentColumns = ["id"],
            childColumns = ["textSetId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LineEntity::class,
            parentColumns = ["id"],
            childColumns = ["linesId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class TextSetLinesEntity(
    val textSetId: Int,
    val linesId: Int
)
