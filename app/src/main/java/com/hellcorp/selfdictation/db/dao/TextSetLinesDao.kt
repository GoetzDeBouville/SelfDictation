package com.hellcorp.selfdictation.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.hellcorp.selfdictation.db.entity.TextSetLinesEntity

@Dao
interface TextSetLinesDao {
    @Upsert
    suspend fun insertLinesSet(setLines: TextSetLinesEntity)

    @Query("SELECT * FROM text_set_lines WHERE textSetId = :setId")
    suspend fun getLinesBySetId(setId: Int) : List<TextSetLinesEntity>
}