package com.hellcorp.selfdictation.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hellcorp.selfdictation.db.entity.TextSetEntity

@Dao
interface TextSetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set: TextSetEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSets(sets: List<TextSetEntity>)

    @Query("SELECT * FROM text_set")
    suspend fun getSet(): List<TextSetEntity>

    @Query("SELECT * FROM text_set WHERE id = :id")
    suspend fun getSetById(id: Int): TextSetEntity?

    @Query("DELETE FROM text_set WHERE id = :id")
    suspend fun removetSet(id: Int)
}