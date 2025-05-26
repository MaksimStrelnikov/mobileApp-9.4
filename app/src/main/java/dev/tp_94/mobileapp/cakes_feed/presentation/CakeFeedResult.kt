package dev.tp_94.mobileapp.cakes_feed.presentation

import dev.tp_94.mobileapp.core.models.CakeGeneral

sealed class CakeFeedResult {
    data class Success(val data: List<CakeGeneral>): CakeFeedResult()
    data class Error(val message: String): CakeFeedResult()
}
