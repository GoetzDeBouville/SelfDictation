package com.hellcorp.selfdictation.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hellcorp.selfdictation.db.entity.LineEntity

@Dao
interface LinesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLine(line: LineEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLines(lines: List<LineEntity>)

    @Query("SELECT * FROM lines_table")
    suspend fun getLines(): List<LineEntity>

    @Query("SELECT * FROM lines_table WHERE id = :id ORDER BY number ASC")
    suspend fun getLineById(id: Int): LineEntity?

    @Query("DELETE FROM lines_table WHERE id = :id")
    suspend fun removetLine(id: Int)
}