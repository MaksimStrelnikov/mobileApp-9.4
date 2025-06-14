package dev.tp_94.mobileapp.self_made_cake.domain

import dev.tp_94.mobileapp.cakes_feed.data.NameBodyDTO
import dev.tp_94.mobileapp.cakes_feed.presentation.Sorting
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

    suspend fun getAllByName(nameBodeDTO: NameBodyDTO): List<CakeResponseDTO>

    suspend fun getAllByNameSorted(nameBodeDTO: NameBodyDTO, sorting: Sorting): List<CakeResponseDTO>

    suspend fun getAllGeneral(): List<CakeResponseDTO>

    suspend fun getAllByConfectioner(confectionerId: Long): List<CakeResponseDTO>

    suspend fun updateGeneralCake(id: Long, cakeGeneralRequestDTO: CakeGeneralRequestDTO, imageUrl: String)
}