package dev.tp_94.mobileapp.customers_feed.presentation

import dev.tp_94.mobileapp.core.models.Confectioner

sealed class FeedResult{
    data class Success(val confectioners: List<Confectioner>) : FeedResult()
    data class Error(val message: String) : FeedResult()
}
