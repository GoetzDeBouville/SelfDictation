package com.hellcorp.selfdictation.domain.models

data class Line(
    val id: Int,
    val number: Int,
    val line: String,
    val letersNum: Int,
    val timeSec: Int
)
