package dev.tp_94.mobileapp.cakesfeed.presentation

import dev.tp_94.mobileapp.core.models.CakeGeneral

data class CakesFeedState(
    val feed: List<CakeGeneral> = ArrayList(),
    val searchText: String = "",
    val isLoading: Boolean = false
)
