package dev.tp_94.mobileapp.add_product.domain

import dev.tp_94.mobileapp.add_product.presentation.AddProductResult
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.login.domain.UserRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
        private val userRepository: UserRepository,
        //TODO
    ) {
        suspend fun execute(
            cake: CakeGeneral
        ): AddProductResult {

            if (cake.name.isEmpty() || cake.description.isEmpty() || cake.diameter == 0f ||
                cake.weight == 0f || cake.preparation == 0 || cake.price == 0) {
                return AddProductResult.Error("Пожалуйста не очищайте параметры при удалении")
            }
            try {
                /*TODO: tokenization - delete from the bd here*/
                return AddProductResult.Success(cake)
            } catch (e: Exception) {
                return AddProductResult.Error(e.message ?: "Возникла непредвиденная ошибка")
            }
        }
    }
