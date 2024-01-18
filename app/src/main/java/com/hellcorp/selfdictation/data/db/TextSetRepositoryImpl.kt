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
import com.hellcorp.selfdictation.ui.main.viewmodels.PairTextSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.text.ParseException

class TextSetRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val textSetDbConverter: TextSetDbConverter,
    private val linesDbConverter: LinesDbConverter
) : TextSetRepository {

    override suspend fun addNewSet(set: TextSet) {
        withContext(Dispatchers.IO) {
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

    override suspend fun getCardByID(setId: Int): Flow<PairTextSet> = flow {
        val set = textSetDbConverter.map(appDatabase.textSetDao().getSetById(setId)!!)
        val setLines = getLineList(setId)
        val pairTextSet = PairTextSet.create(set, setLines.first())
        emit(pairTextSet)
    }.flowOn(Dispatchers.IO)

    override suspend fun getSetList(): Flow<List<TextSet>> = flow {
        val setList = appDatabase.textSetDao().getSet()
        emit(convertSetFromEmtity(setList.sortedBy { it.name }))
    }.flowOn(Dispatchers.IO)

    override suspend fun getLineList(setId: Int): Flow<List<Line>> = flow {
        val textSetLines = appDatabase.textSetLinesDao().getLinesBySetId(setId)
        val lineList = textSetLines.mapNotNull { appDatabase.linesDao().getLineById(it.linesId) }
        emit(convertLineFromEmtity(lineList.sortedBy { it.number }))
    }.flowOn(Dispatchers.IO)

    override suspend fun getLastIdSet(): Flow<Int> = flow {
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
