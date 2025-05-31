package dev.tp_94.mobileapp.core.api

import dev.tp_94.mobileapp.custom_order_settings.data.RestrictionsRequestDTO
import dev.tp_94.mobileapp.custom_order_settings.data.RestrictionsResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface RestrictionsApi {
    @GET("confectioners/{id}/settings")
    suspend fun getCustomCakeRestrictions(@Path("id") confectionerId: Long): Response<RestrictionsResponseDTO>

    @PUT("confectioners/self/settings")
    suspend fun updateCustomCakeRestrictions(
        @Path("id") confectionerId: Long,
        @Body restrictions: RestrictionsRequestDTO
    ): Response<RestrictionsResponseDTO>
}