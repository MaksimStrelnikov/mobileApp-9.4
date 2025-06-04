package dev.tp_94.mobileapp.self_made_cake.domain

import dev.tp_94.mobileapp.add_product.CakeGeneralUpdateRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeCustomRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeGeneralRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeResponseDTO

interface CakeRepository {
    suspend fun addCustomCake(
        cakeCustomRequestDTO: CakeCustomRequestDTO,
        imageUrl: String?
    ): CakeResponseDTO

    suspend fun addGeneralCake(
        cakeGeneralRequestDTO: CakeGeneralRequestDTO,
        imageUrl: String
    ): CakeResponseDTO

    suspend fun deleteCake(id: Long)

    suspend fun getAllByName(text: String): List<CakeResponseDTO>

    suspend fun getAllGeneral(): List<CakeResponseDTO>

    suspend fun getAllByConfectioner(confectionerId: Long): List<CakeResponseDTO>

    suspend fun updateGeneralCake(id: Long, cakeGeneralUpdateRequestDTO: CakeGeneralUpdateRequestDTO, imageUrl: String)
}