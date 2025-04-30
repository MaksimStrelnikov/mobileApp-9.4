package dev.tp_94.mobileapp.customersfeed.presentation

import dev.tp_94.mobileapp.core.models.Confectioner

data class CustomersFeedState (
    val feed: List<Confectioner> = ArrayList(),
    val searchText: String = "",
    val isLoading: Boolean = false
)