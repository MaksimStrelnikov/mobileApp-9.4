package dev.tp_94.mobileapp.add_product.domain

import dev.tp_94.mobileapp.add_product.presentation.AddProductResult
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.login.domain.UserRepository
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val userRepository: UserRepository,
    //TODO
) {
    suspend fun execute(
        cake: CakeGeneral
    ): AddProductResult {

        if (cake.name.isEmpty()) {
            return AddProductResult.Error("Название не указано")
        }
        if (cake.description.isEmpty()) {
            return AddProductResult.Error("Описание не указано")
        }
        if (cake.diameter <= 0) {
            return AddProductResult.Error("Диаметр указан неверно")
        }
        if (cake.weight <= 0) {
            return AddProductResult.Error("Вес указан неверно")
        }
        if (cake.preparation <= 0) {
            return AddProductResult.Error("Время приготовления указано неверно")
        }
        if (cake.price <= 0) {
            return AddProductResult.Error("Цена указана неверно")
        }

        try {
           /*TODO: tokenization - Save or update to the bd here*/
            return AddProductResult.Success()
        } catch (e: Exception) {
            return AddProductResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }

}