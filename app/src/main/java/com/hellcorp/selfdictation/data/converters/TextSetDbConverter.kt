package com.hellcorp.selfdictation.data.converters

import com.hellcorp.selfdictation.db.entity.TextSetEntity
import com.hellcorp.selfdictation.domain.models.TextSet

class TextSetDbConverter {
    fun map(set: TextSetEntity) = TextSet(
        id = set.id,
        name = set.name
    )

    fun map(set: TextSet) = TextSetEntity(
        id = set.id,
        name = set.name
    )
}