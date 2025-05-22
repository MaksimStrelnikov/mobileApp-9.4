package dev.tp_94.mobileapp.customers_feed.domain

import dev.tp_94.mobileapp.core.models.Confectioner

interface ConfectionerRepository {
    //TODO: add pagination
    suspend fun getAllByName(name: String): List<Confectioner>
    suspend fun getAll(): List<Confectioner>
}