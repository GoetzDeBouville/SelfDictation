package com.hellcorp.selfdictation.domain.models

import android.util.Pair
import com.hellcorp.selfdictation.ui.main.viewmodels.PairTextSet

sealed interface SetListState {
    data object Empty : SetListState
    data object Loading : SetListState
    data class Content(
        val data: List<PairTextSet>,
        val setsNumber: Int
    ) : SetListState
}