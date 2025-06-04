package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.api.GenerationApi
import dev.tp_94.mobileapp.self_made_cake_generator.data.GenerationRequestDTO
import dev.tp_94.mobileapp.self_made_cake_generator.data.GenerationResponseDTO
import dev.tp_94.mobileapp.self_made_cake_generator.domain.GenerationRepository
import javax.inject.Inject

class GenerationRepositoryImpl @Inject constructor(private val api: GenerationApi): GenerationRepository {
    override suspend fun generateCake(generationRequestDTO: GenerationRequestDTO): GenerationResponseDTO {
        val response = api.generateCake(generationRequestDTO)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }
}