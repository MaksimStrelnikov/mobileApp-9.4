package dev.tp_94.mobileapp.customers_feed.presentation

import dev.tp_94.mobileapp.core.models.Confectioner

data class CustomersFeedState (
    val feed: List<Confectioner> = ArrayList(),
    val searchText: String = "",
    val currentSorting: Sorting = Sorting.NO_SORTING,
    val isLoading: Boolean = false
)

enum class Sorting(val text: String) {
    NO_SORTING("По умолчанию"),
    /*BY_FILLINGS_UP("По начинкам (сначала больше)"),
    BY_FILLINGS_DOWN("По начинкам (сначала меньше)"),*/
    BY_CAKES_UP("По товарам (сначала больше)"),
    BY_CAKES_DOWN("По товарам (сначала меньше)");

    override fun toString(): String {
        return text
    }
}