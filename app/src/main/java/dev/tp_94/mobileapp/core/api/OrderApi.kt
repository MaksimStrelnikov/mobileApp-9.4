package dev.tp_94.mobileapp.core.api

import dev.tp_94.mobileapp.orders.data.dto.OrderResponseDTO
import dev.tp_94.mobileapp.payment.data.CardDTO
import dev.tp_94.mobileapp.payment.data.SeveralOrdersCreationPaymentRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderFullRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderPricePatchRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderStatusPatchRequestDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderApi {
    @POST("orders/self")
    suspend fun createOrder(@Body order: OrderRequestDTO): Response<OrderResponseDTO>

    @POST("orders/full/self")
    suspend fun createOrder(@Body order: OrderFullRequestDTO): Response<OrderResponseDTO>

    @GET("orders/customer/self")
    suspend fun getAllCustomerOrders(): Response<List<OrderResponseDTO>>

    @GET("orders/confectioner/self")
    suspend fun getAllConfectionerOrders(): Response<List<OrderResponseDTO>>

    @PUT("orders/{id}/status")
    suspend fun updateStatusOrder(@Path("id") orderId: Long, @Body order: OrderStatusPatchRequestDTO): Response<OrderResponseDTO>

    @PATCH("orders/{id}")
    suspend fun updatePriceOrder(@Path("id") orderId: Long, @Body order: OrderPricePatchRequestDTO): Response<OrderResponseDTO>

    @POST("orders/batchpay")
    suspend fun createAndPayOrders(@Body severalOrdersCreationPaymentRequestDTO: SeveralOrdersCreationPaymentRequestDTO): Response<Unit>

    @POST("orders/{id}/pay")
    suspend fun payOrder(@Path("id") orderId: Long, @Body cardDTO: CardDTO): Response<Unit>

    @POST("orders/{id}/receive")
    suspend fun receiveOrder(@Path("id") orderId: Long): Response<OrderResponseDTO>
}