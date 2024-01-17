package com.hellcorp.selfdictation.domain

import com.hellcorp.selfdictation.domain.models.Line
import com.hellcorp.selfdictation.domain.models.TextSet
import kotlinx.coroutines.flow.Flow

interface TextSetInteractor {
    suspend fun addNewSet(set: TextSet)
//    suspend fun addLineToSet(setId: Int, line: Line) : Flow<Boolean>
    suspend fun addLineToSet(setId: Int, line: Line)
    suspend fun updateSet(setId: Int)
    fun getSetList(): Flow<List<TextSet>>
    fun getLineList(setId: Int): Flow<List<Line>>
    fun getLastIdSet(): Flow<Int>
    suspend fun removeSet(id: Int)
    suspend fun removeLine(id: Int)
}