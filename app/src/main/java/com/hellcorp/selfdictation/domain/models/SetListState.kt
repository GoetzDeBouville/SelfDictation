package com.hellcorp.selfdictation.domain.models

sealed interface SetListState {
    object Empty : SetListState
    data class Content(val set: List<TextSet>) : SetListState
}