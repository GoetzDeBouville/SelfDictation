package com.hellcorp.selfdictation.data.db

import com.hellcorp.selfdictation.data.converters.LinesDbConverter
import com.hellcorp.selfdictation.data.converters.TextSetDbConverter
import com.hellcorp.selfdictation.db.AppDatabase
import com.hellcorp.selfdictation.db.entity.LineEntity
import com.hellcorp.selfdictation.db.entity.TextSetEntity
import com.hellcorp.selfdictation.db.entity.TextSetLinesEntity
import com.hellcorp.selfdictation.domain.Line
import com.hellcorp.selfdictation.domain.TextSet
import com.hellcorp.selfdictation.domain.TextSetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TextSetRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val textSetDbConverter: TextSetDbConverter,
    private val linesDbConverter: LinesDbConverter
) : TextSetRepository {

    override suspend fun addNewSet(set: TextSet) {
        val entity = textSetDbConverter.map(set)
        appDatabase.textSetDao().insertSet(entity)
    }

    override suspend fun addLineToSet(set: TextSet, line: Line): Flow<Boolean> = flow {
        if (appDatabase.textSetLinesDao().getLinesBySetId(set.id)
                .contains(TextSetLinesEntity(set.id, line.id))
        ) {
            emit(false)
            return@flow
        }
        appDatabase.linesDao().insertLine(linesDbConverter.map(line))
        val setLines = TextSetLinesEntity(set.id, line.id)
        appDatabase.textSetLinesDao().insertLinesSet(setLines)

        updateSet(set)
        emit(true)
    }

    override suspend fun updateSet(set: TextSet) {
        appDatabase.textSetDao().insertSet(textSetDbConverter.map(set))
    }

    override fun getSetList(): Flow<List<TextSet>> = flow {
        val setList = appDatabase.textSetDao().getSet()
        emit(convertSetFromEmtity(setList))
    }.flowOn(Dispatchers.IO)

    override fun getLineList(setId: Int): Flow<List<Line>> = flow {
        val textSetLines = appDatabase.textSetLinesDao().getLinesBySetId(setId)
        val lineList = textSetLines.mapNotNull { appDatabase.linesDao().getLineById(setId) }
        emit(convertLineFromEmtity(lineList))
    }.flowOn(Dispatchers.IO)


    override suspend fun removeSet(id: Int) {
        appDatabase.textSetDao().removetSet(id)
    }

    override suspend fun removeLine(id: Int) {
        appDatabase.linesDao().removetLine(id)
    }

    private fun convertSetFromEmtity(set: List<TextSetEntity>): List<TextSet> = set.map {
        textSetDbConverter.map(it)
    }

    private fun convertLineFromEmtity(set: List<LineEntity>): List<Line> = set.map {
        linesDbConverter.map(it)
    }
}
