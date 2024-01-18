package com.hellcorp.selfdictation.ui.newcard

import com.hellcorp.selfdictation.ui.main.viewmodels.PairTextSet

sealed interface CardState {
    data object Empty : CardState
    data class Content(val data: PairTextSet) : CardState
}