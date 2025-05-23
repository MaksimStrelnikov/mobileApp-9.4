package dev.tp_94.mobileapp.self_made_cake.domain

import androidx.compose.ui.graphics.toArgb
import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeCustomRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderRequestDTO
import dev.tp_94.mobileapp.self_made_cake.presentation.SelfMadeCakeResult
import javax.inject.Inject

class SendCustomCakeUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val cakeRepository: CakeRepository
) {
    suspend fun execute(
        cake: CakeCustom,
        customer: Customer,
        confectioner: Confectioner
    ): SelfMadeCakeResult {
        try {
            val cakeResponseDTO = cakeRepository.addCustomCake(
                cakeCustomRequestDTO = CakeCustomRequestDTO(
                    confectionerId = confectioner.id,
                    name = cake.name,
                    description = cake.description,
                    fillings = cake.fillings,
                    reqTime = cake.preparation,
                    color = cake.color.toArgb().toString(),
                    diameter = cake.diameter,
                    text = cake.text,
                    textSize = 14,
                    textX = cake.textOffset.x,
                    textY = cake.textOffset.y,
                    price = 0
                ),
                imageUri = cake.imageUrl
            )
            orderRepository.placeOrder(
                OrderRequestDTO(
                    cakeId = cakeResponseDTO.id,
                    customerId = customer.id,
                    price = 0,
                    quantity = 1
                ),
            )
            return SelfMadeCakeResult.Success()
        } catch (e: Exception) {
            return SelfMadeCakeResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}