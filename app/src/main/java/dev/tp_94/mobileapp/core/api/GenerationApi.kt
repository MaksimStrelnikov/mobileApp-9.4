package dev.tp_94.mobileapp.core.api

import dev.tp_94.mobileapp.self_made_cake_generator.data.GenerationRequestDTO
import dev.tp_94.mobileapp.self_made_cake_generator.data.GenerationResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GenerationApi {
    @POST("generate")
    suspend fun generateCake(@Body generationRequestDTO: GenerationRequestDTO): Response<GenerationResponseDTO>
}