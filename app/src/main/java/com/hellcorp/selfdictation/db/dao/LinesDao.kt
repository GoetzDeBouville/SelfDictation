package com.hellcorp.selfdictation.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.hellcorp.selfdictation.db.entity.LineEntity

@Dao
interface LinesDao {
    @Upsert
    suspend fun insertLine(line: LineEntity)

    @Upsert
    suspend fun insertLines(lines: List<LineEntity>)

    @Query("SELECT * FROM lines_table")
    suspend fun getLines(): List<LineEntity>

    @Query("SELECT * FROM lines_table WHERE id = :id ORDER BY number ASC")
    suspend fun getLineById(id: Int): LineEntity?

    @Query("DELETE FROM lines_table WHERE id = :id")
    suspend fun removeLineById(id: Int)
}