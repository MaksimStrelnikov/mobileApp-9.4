package dev.tp_94.mobileapp.customers_feed.domain

import dev.tp_94.mobileapp.cakes_feed.data.NameBodyDTO
import dev.tp_94.mobileapp.customers_feed.presentation.Sorting
import dev.tp_94.mobileapp.orders.data.dto.ConfectionerResponseDTO

interface ConfectionerRepository {
    //TODO: add pagination
    suspend fun getAllByName(name: NameBodyDTO): List<ConfectionerResponseDTO>
    suspend fun getAllByNameSorted(name: NameBodyDTO, sorting: Sorting): List<ConfectionerResponseDTO>
    suspend fun getAll(): List<ConfectionerResponseDTO>
}