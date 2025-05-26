package dev.tp_94.mobileapp.core.api

import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderFullRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderRequestDTO
import dev.tp_94.mobileapp.orders.data.dto.OrderResponseDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderPatchRequestDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApi {
    @POST("orders/self")
    suspend fun createOrder(@Body order: OrderRequestDTO): Response<OrderResponseDTO>

    @POST("orders/full/self")
    suspend fun createOrder(@Body order: OrderFullRequestDTO): Response<OrderResponseDTO>

    @GET("orders")
    suspend fun getAllOrders(): Response<List<OrderResponseDTO>>

    @PATCH("orders/{id}")
    suspend fun updateStatusOrder(@Path("id") orderId: Long, @Body order: OrderPatchRequestDTO): Response<OrderResponseDTO>
}