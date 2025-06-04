package dev.tp_94.mobileapp.cakes_feed.presentation

import dev.tp_94.mobileapp.core.models.CakeGeneral

data class CakesFeedState(
    val feed: List<CakeGeneral> = ArrayList(),
    val searchText: String = "",
    val currentSorting: Sorting = Sorting.NO_SORTING,
    val isLoading: Boolean = false
)

enum class Sorting(val text: String, val ascending: Boolean) {
    NO_SORTING("По умолчанию", false),
    BY_WEIGHT_UP("По весу (сначала лёгкие)", true),
    BY_WEIGHT_DOWN("По весу (сначала тяжёлые)", false),
    BY_PRICE_UP("По цене (сначала дешёвые)", true),
    BY_PRICE_DOWN("По цене (сначала дорогие)", false);

    override fun toString(): String {
        return text
    }
}
