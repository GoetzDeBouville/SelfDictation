package com.hellcorp.selfdictation.domain

import com.hellcorp.selfdictation.domain.models.Line
import com.hellcorp.selfdictation.domain.models.TextSet
import com.hellcorp.selfdictation.ui.main.viewmodels.PairTextSet
import kotlinx.coroutines.flow.Flow

interface TextSetInteractor {
    suspend fun addNewSet(set: TextSet)
    suspend fun addLineToSet(setId: Int, line: Line)
    suspend fun updateSet(setId: Int)
    suspend fun getCardById(setId: Int) : Flow<PairTextSet>
    suspend fun getSetList(): Flow<List<TextSet>>
    suspend fun getLineList(setId: Int): Flow<List<Line>>
    suspend fun getLastIdSet(): Flow<Int>
    suspend fun removeSet(id: Int)
    suspend fun removeLine(id: Int)
}