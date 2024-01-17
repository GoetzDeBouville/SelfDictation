package com.hellcorp.selfdictation.data.db

import android.util.Log
import com.hellcorp.selfdictation.data.converters.LinesDbConverter
import com.hellcorp.selfdictation.data.converters.TextSetDbConverter
import com.hellcorp.selfdictation.db.AppDatabase
import com.hellcorp.selfdictation.db.entity.LineEntity
import com.hellcorp.selfdictation.db.entity.TextSetEntity
import com.hellcorp.selfdictation.db.entity.TextSetLinesEntity
import com.hellcorp.selfdictation.domain.models.Line
import com.hellcorp.selfdictation.domain.models.TextSet
import com.hellcorp.selfdictation.domain.TextSetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class TextSetRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val textSetDbConverter: TextSetDbConverter,
    private val linesDbConverter: LinesDbConverter
) : TextSetRepository {

    override suspend fun addNewSet(set: TextSet) {
        withContext(Dispatchers.IO)  {
            val entity = textSetDbConverter.map(set)
            appDatabase.textSetDao().insertSet(entity)
        }
    }

    override suspend fun addLineToSet(setId: Int, line: Line) {
        withContext(Dispatchers.IO) {
            appDatabase.linesDao().insertLine(linesDbConverter.map(line))
            val setLinesEntity = TextSetLinesEntity(setId, appDatabase.linesDao().getLastId())
            appDatabase.textSetLinesDao().insertLinesSet(setLinesEntity)
        }
    }

    override suspend fun updateSet(setId: Int) {
        withContext(Dispatchers.IO) {
            val textSet = appDatabase.textSetDao().getSetById(setId)
            if (textSet != null) {
                appDatabase.textSetDao().insertSet(textSet)
            }
        }
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

    override fun getLastIdSet(): Flow<Int> = flow {
        emit(appDatabase.textSetDao().getLastId())
    }.flowOn(Dispatchers.IO)

    override suspend fun removeSet(id: Int) {
        appDatabase.textSetDao().removeSet(id)
    }

    override suspend fun removeLine(id: Int) {
        appDatabase.linesDao().removeLineById(id)
    }

    private fun convertSetFromEmtity(set: List<TextSetEntity>): List<TextSet> = set.map {
        textSetDbConverter.map(it)
    }

    private fun convertLineFromEmtity(set: List<LineEntity>): List<Line> = set.map {
        linesDbConverter.map(it)
    }
}
