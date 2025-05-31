package dev.tp_94.mobileapp.add_product.domain

import dev.tp_94.mobileapp.add_product.presentation.ProductResult
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.login.domain.UserRepository
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeGeneralRequestDTO
import dev.tp_94.mobileapp.self_made_cake.domain.CakeRepository
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val repository: CakeRepository,
    private val sessionCache: SessionCache
) {
    suspend fun execute(
        cake: CakeGeneral,
        isUpdate: Boolean
    ): ProductResult {
        if (sessionCache.session.value == null || sessionCache.session.value!!.user !is Confectioner) return ProductResult.Error(
            "Недостаточно прав для добавления продукта"
        )
        if (cake.imageUrl == null) {
            return ProductResult.Error("Изображение не указано")
        }
        if (cake.name.isEmpty()) {
            return ProductResult.Error("Название не указано")
        }
        if (cake.description.isEmpty()) {
            return ProductResult.Error("Описание не указано")
        }
        if (cake.diameter <= 0f) {
            return ProductResult.Error("Диаметр указан неверно")
        }
        if (cake.weight <= 0f) {
            return ProductResult.Error("Вес указан неверно")
        }
        if (cake.preparation <= 0) {
            return ProductResult.Error("Время приготовления указано неверно")
        }
        if (cake.price <= 0) {
            return ProductResult.Error("Цена указана неверно")
        }
        try {
            if (isUpdate) {
                repository.updateGeneralCake(
                    CakeGeneralRequestDTO(
                        confectionerId = sessionCache.session.value!!.user.id,
                        name = cake.name,
                        description = cake.description,
                        diameter = cake.diameter,
                        weight = cake.weight,
                        reqTime = cake.preparation,
                        price = cake.price
                    ),
                    imageUrl = cake.imageUrl
                )
            }
            else {
                repository.addGeneralCake(
                    CakeGeneralRequestDTO(
                        confectionerId = sessionCache.session.value!!.user.id,
                        name = cake.name,
                        description = cake.description,
                        diameter = cake.diameter,
                        weight = cake.weight,
                        reqTime = cake.preparation,
                        price = cake.price
                    ),
                    imageUrl = cake.imageUrl
                )
            }
            return ProductResult.Success
        } catch (e: Exception) {
            return ProductResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }

}