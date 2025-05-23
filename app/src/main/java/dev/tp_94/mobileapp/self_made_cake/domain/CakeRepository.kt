package dev.tp_94.mobileapp.self_made_cake.domain

import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeCustomRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeResponseDTO

interface CakeRepository {
    suspend fun addCustomCake(cakeCustomRequestDTO: CakeCustomRequestDTO, imageUri: String?): CakeResponseDTO
}