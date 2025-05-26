package dev.tp_94.mobileapp.core.api

import dev.tp_94.mobileapp.orders.data.dto.ConfectionerResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface ConfectionerApi {
    @GET("confectioners")
    suspend fun getConfectioners(): Response<List<ConfectionerResponseDTO>>
}