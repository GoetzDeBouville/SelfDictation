package com.hellcorp.selfdictation.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.hellcorp.selfdictation.db.entity.TextSetEntity

@Dao
interface TextSetDao {
    @Upsert
    suspend fun insertSet(set: TextSetEntity)

    @Query("SELECT * FROM text_set")
    suspend fun getSet(): List<TextSetEntity>

    @Query("SELECT * FROM text_set WHERE id = :id")
    suspend fun getSetById(id: Int): TextSetEntity?

    @Query("SELECT MAX(id) FROM text_set")
    suspend fun getLastId(): Int

    @Query("DELETE FROM text_set WHERE id = :id")
    suspend fun removeSet(id: Int)
}