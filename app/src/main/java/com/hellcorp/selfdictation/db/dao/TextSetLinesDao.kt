package com.hellcorp.selfdictation.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hellcorp.selfdictation.db.entity.TextSetLinesEntity

@Dao
interface TextSetLinesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLinesSet(line: TextSetLinesEntity)

    @Query("SELECT * FROM text_set_lines WHERE textSetId = :setId")
    suspend fun getLinesBySetId(setId: Int) : List<TextSetLinesEntity>
}