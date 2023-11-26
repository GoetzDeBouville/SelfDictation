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

    override suspend fun addLineToSet(set: TextSet, line: Line): Flow<Boolean> {
        return repository.addLineToSet(set, line)
    }

    override suspend fun updateSet(set: TextSet) {
        repository.updateSet(set)
    }

    override fun getSetList(): Flow<List<TextSet>> {
        return repository.getSetList()
    }

    override fun getLineList(setId: Int): Flow<List<Line>> {
        return repository.getLineList(setId)
    }

    override suspend fun removeSet(id: Int) {
        repository.removeSet(id)
    }

    override suspend fun removeLine(id: Int) {
        repository.removeLine(id)
    }
}