package dev.tp_94.mobileapp.core.api

import dev.tp_94.mobileapp.core.api.dto.RefreshResponseDTO
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface RefreshApi {
    @POST("auth/refresh/")
    suspend fun refreshToken(@Header("Authorization") refreshToken: String): Response<RefreshResponseDTO>
}