package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.cakes_feed.data.NameBodyDTO
import dev.tp_94.mobileapp.core.api.ConfectionerApi
import dev.tp_94.mobileapp.customers_feed.domain.ConfectionerRepository
import dev.tp_94.mobileapp.customers_feed.presentation.Sorting
import dev.tp_94.mobileapp.orders.data.dto.ConfectionerResponseDTO
import javax.inject.Inject

class ConfectionerRepositoryImpl @Inject constructor(
    private val confectionerApi: ConfectionerApi
): ConfectionerRepository {
    override suspend fun getAllByName(name: NameBodyDTO): List<ConfectionerResponseDTO> {
        val response = confectionerApi.getConfectionersByName(name)
        if (!response.isSuccessful) throw Exception(response.message())
        return response.body() ?: emptyList()
    }

    override suspend fun getAllByNameSorted(
        name: NameBodyDTO,
        sorting: Sorting
    ): List<ConfectionerResponseDTO> {
        val response = confectionerApi.getConfectionersByName(sorting.ascending,  name)
        if (!response.isSuccessful) throw Exception(response.message())
        return response.body() ?: emptyList()
    }

    override suspend fun getAll(): List<ConfectionerResponseDTO> {
        val response = confectionerApi.getConfectioners()
        if (!response.isSuccessful) throw Exception(response.message())
        return response.body() ?: emptyList()
    }
}