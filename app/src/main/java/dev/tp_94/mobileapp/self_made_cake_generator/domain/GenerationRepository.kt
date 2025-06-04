package dev.tp_94.mobileapp.self_made_cake_generator.domain

import dev.tp_94.mobileapp.self_made_cake_generator.data.GenerationRequestDTO
import dev.tp_94.mobileapp.self_made_cake_generator.data.GenerationResponseDTO

interface GenerationRepository {
    suspend fun generateCake(generationRequestDTO: GenerationRequestDTO): GenerationResponseDTO
}
