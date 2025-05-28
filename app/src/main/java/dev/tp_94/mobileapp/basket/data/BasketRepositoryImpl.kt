package dev.tp_94.mobileapp.basket.data

import dev.tp_94.mobileapp.basket.domain.BasketRepository
import dev.tp_94.mobileapp.core.models.CakeGeneral
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(basketDatabase: BasketDatabase) : BasketRepository {
    private val dao = basketDatabase.basketDao()
    override suspend fun getBasket(userPhone: String): List<CakeGeneral> {
        return dao.getBasket(userPhone).map { it.cake }
    }

    override suspend fun clearBasket(userPhone: String) {
        dao.clearBasket(userPhone)
    }

    override suspend fun addToBasket(item: CakeGeneral, userPhone: String) {
        dao.insertCakeGeneral(
            CakeGeneralEntity(
                userPhone = userPhone,
                cake = item
            )
        )
    }

    override suspend fun removeFromBasket(item: CakeGeneral, userPhone: String) {
        val result = dao.findOneBasketEntry(
            userPhone = userPhone,
            cake = item
        )
        if (result == null) {
            return;
        } else {
            dao.deleteOneBasketEntry(result)
        }

    }

}