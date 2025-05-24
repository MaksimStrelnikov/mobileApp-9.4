package dev.tp_94.mobileapp.add_product.domain

import dev.tp_94.mobileapp.add_product.presentation.AddProductResult
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.login.domain.UserRepository
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val userRepository: UserRepository,
    //TODO
) {
    suspend fun addProduct(
        name: String,
        description: String,
        diameter: String,
        weight: String,
        workPeriod: String,
        price: String,
        imageUrl: String?,
        confectioner: Confectioner
    ): AddProductResult {

        if (name.isEmpty()) {
            return AddProductResult.Error("Название не указано")
        }
        if (description.isEmpty()) {
            return AddProductResult.Error("Описание не указано")
        }
        if (diameter.isEmpty() || diameter.toFloat() <= 0) {
            return AddProductResult.Error("Диаметр указан неверно")
        }
        if (weight.isEmpty() || weight.toFloat() <= 0) {
            return AddProductResult.Error("Вес указан неверно")
        }
        if (workPeriod.isEmpty() || workPeriod.toInt() <= 0) {
            return AddProductResult.Error("Время приготовления указано неверно")
        }
        if (price.isEmpty() || price.toInt() <= 0) {
            return AddProductResult.Error("Цена указана неверно")
        }

        try {

            val cake = CakeGeneral(
                name = name,
                description = description,
                diameter = diameter.toFloat(),
                weight = weight.toFloat(),
                preparation = workPeriod.toInt(),
                price = price.toInt(),
                imageUrl = imageUrl,
                confectioner = confectioner
            )
           /*TODO: tokenization - Save or update to the bd here*/
            return AddProductResult.Success(cake)
        } catch (e: Exception) {
            return AddProductResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }

    suspend fun deleteProduct(
        name: String,
        description: String,
        diameter: String,
        weight: String,
        workPeriod: String,
        price: String,
        imageUrl: String?,
        confectioner: Confectioner
    ): AddProductResult {

        if (name.isEmpty() || description.isEmpty() || diameter.isEmpty() ||
            weight.isEmpty() || workPeriod.isEmpty() || price.isEmpty()) {
            return AddProductResult.Error("Пожалуйста не очищайте параметры при удалении")
        }
        try {
            val cake = CakeGeneral(
                name = name,
                description = description,
                diameter = diameter.toFloat(),
                weight = weight.toFloat(),
                preparation = workPeriod.toInt(),
                price = price.toInt(),
                imageUrl = imageUrl,
                confectioner = confectioner
            )
            /*TODO: tokenization - delete from the bd here*/
            return AddProductResult.Success(cake)
        } catch (e: Exception) {
            return AddProductResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}