package dev.tp_94.mobileapp.self_made_cake_generator.domain

interface GenerationRepository {
    suspend fun generateCake(): String?
}
