package com.hellcorp.selfdictation.domain.impl

import com.hellcorp.selfdictation.domain.TextSetInteractor
import com.hellcorp.selfdictation.domain.TextSetRepository
import com.hellcorp.selfdictation.domain.models.Line
import com.hellcorp.selfdictation.domain.models.TextSet
import kotlinx.coroutines.flow.Flow

class TextSetInteractorImpl(private val repository: TextSetRepository) : TextSetInteractor {
    override suspend fun addNewSet(set: TextSet) {
        repository.addNewSet(set)
    }

    override suspend fun addLineToSet(setId: Int, line: Line) {
        return repository.addLineToSet(setId, line)
    }

    override suspend fun updateSet(setId: Int) {
        repository.updateSet(setId)
    }

    override fun getSetList(): Flow<List<TextSet>> {
        return repository.getSetList()
    }

    override fun getLineList(setId: Int): Flow<List<Line>> {
        return repository.getLineList(setId)
    }

    override fun getLastIdSet(): Flow<Int> {
        return repository.getLastIdSet()
    }

    override suspend fun removeSet(id: Int) {
        repository.removeSet(id)
    }

    override suspend fun removeLine(id: Int) {
        repository.removeLine(id)
    }
}