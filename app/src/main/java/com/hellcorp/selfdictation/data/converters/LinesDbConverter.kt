package com.hellcorp.selfdictation.data.converters

import com.hellcorp.selfdictation.db.entity.LineEntity
import com.hellcorp.selfdictation.domain.models.Line

class LinesDbConverter {
    fun map(line: LineEntity) = Line(
        id = line.id,
        number = line.number,
        line = line.line,
        letersNum = line.letersNum,
        timeSec = line.timeSec
    )

    fun map(line: Line) = LineEntity(
        id = line.id,
        number = line.number,
        line = line.line,
        letersNum = line.letersNum,
        timeSec = line.timeSec
    )
}