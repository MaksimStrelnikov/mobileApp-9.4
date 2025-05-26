package dev.tp_94.mobileapp.core.api

import dev.tp_94.mobileapp.self_made_cake_generator.data.GenerationResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface GenerationApi {
    @GET("generate/async")
    suspend fun generateCake(): Response<GenerationResponseDTO>
}