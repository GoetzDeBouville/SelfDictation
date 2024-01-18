package com.hellcorp.selfdictation.domain.impl

import com.hellcorp.selfdictation.domain.TextSetInteractor
import com.hellcorp.selfdictation.domain.TextSetRepository
import com.hellcorp.selfdictation.domain.models.Line
import com.hellcorp.selfdictation.domain.models.TextSet
import com.hellcorp.selfdictation.ui.main.viewmodels.PairTextSet
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

    override suspend fun getCardById(setId: Int): Flow<PairTextSet> {
        return repository.getCardByID(setId)
    }

    override suspend fun getSetList(): Flow<List<TextSet>> {
        return repository.getSetList()
    }

    override suspend fun getLineList(setId: Int): Flow<List<Line>> {
        return repository.getLineList(setId)
    }

    override suspend fun getLastIdSet(): Flow<Int> {
        return repository.getLastIdSet()
    }

    override suspend fun removeSet(id: Int) {
        repository.removeSet(id)
    }

    override suspend fun removeLine(id: Int) {
        repository.removeLine(id)
    }
}