package com.hellcorp.selfdictation.domain.models

import android.util.Pair

typealias PairTextSet = Pair<TextSet, List<Line>>
sealed interface SetListState {
    data object Empty : SetListState
    data object Loading : SetListState
    data class Content(val set: List<PairTextSet>) : SetListState
}