package dev.tp_94.mobileapp.basket.domain

import dev.tp_94.mobileapp.core.models.CakeGeneral

interface BasketRepository {
    /**
     * Get the list of cakes in the basket
     * @return List<CakeGeneral>
     */
    suspend fun getBasket(userPhone: String): List<CakeGeneral>

    /**
     * Updates basket in memory by replacing the old list with the new one
     * @param items list of general cakes
     * @param userPhone user phone
     * @return List<CakeGeneral>
     */
    suspend fun updateBasket(items: List<CakeGeneral>, userPhone: String): List<CakeGeneral>

    /**
     * Clears the basket
     * @param userPhone user phone
     */
    suspend fun clearBasket(userPhone: String)

    /**
     * Adds a cake to the basket
     * @param item general cake
     * @param userPhone user phone
     */
    suspend fun addToBasket(item: CakeGeneral, userPhone: String)
}