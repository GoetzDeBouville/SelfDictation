package com.hellcorp.selfdictation.domain

import com.hellcorp.selfdictation.domain.models.Line
import com.hellcorp.selfdictation.domain.models.TextSet
import kotlinx.coroutines.flow.Flow

interface TextSetRepository {
    suspend fun addNewSet(set: TextSet)
    suspend fun addLineToSet(set: TextSet, line: Line) : Flow<Boolean>
    suspend fun updateSet(set: TextSet)
    fun getSetList(): Flow<List<TextSet>>
    fun getLineList(setId: Int): Flow<List<Line>>
    suspend fun removeSet(id: Int)
    suspend fun removeLine(id: Int)
}