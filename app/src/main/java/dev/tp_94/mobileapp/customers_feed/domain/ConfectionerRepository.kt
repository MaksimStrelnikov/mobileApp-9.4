package dev.tp_94.mobileapp.customers_feed.domain

import dev.tp_94.mobileapp.orders.data.dto.ConfectionerResponseDTO

interface ConfectionerRepository {
    //TODO: add pagination
    suspend fun getAllByName(name: String): List<ConfectionerResponseDTO>
    suspend fun getAll(): List<ConfectionerResponseDTO>
}