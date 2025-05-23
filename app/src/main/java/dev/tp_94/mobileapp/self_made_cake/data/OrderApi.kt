package dev.tp_94.mobileapp.self_made_cake.data

import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderFullRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderRequestDTO
import dev.tp_94.mobileapp.orders.data.OrderResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApi {
    @POST("orders")
    suspend fun createOrder(@Body order: OrderRequestDTO): Response<OrderResponseDTO>

    @POST("orders/full")
    suspend fun createOrder(@Body order: OrderFullRequestDTO): Response<OrderResponseDTO>
}