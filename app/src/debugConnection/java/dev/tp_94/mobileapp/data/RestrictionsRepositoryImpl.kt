package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.data.HttpStatus
import dev.tp_94.mobileapp.custom_order_settings.data.RestrictionsRequestDTO
import dev.tp_94.mobileapp.core.api.RestrictionsApi
import dev.tp_94.mobileapp.custom_order_settings.data.RestrictionsResponseDTO
import dev.tp_94.mobileapp.self_made_cake.domain.RestrictionsRepository
import javax.inject.Inject

class RestrictionsRepositoryImpl @Inject constructor(
    private val restrictionsApi: RestrictionsApi
): RestrictionsRepository {
    override suspend fun getCustomCakeRestrictions(id: Long): RestrictionsResponseDTO {
        val response = restrictionsApi.getCustomCakeRestrictions(id)
        if (response.code() != HttpStatus.OK.status) throw Exception(response.message())
        return response.body()!!
    }

    override suspend fun updateCustomCakeRestrictions(confectionerId: Long, restrictions: RestrictionsRequestDTO): RestrictionsResponseDTO {
        val response = restrictionsApi.updateCustomCakeRestrictions(confectionerId, restrictions)
        if (response.code() != HttpStatus.UPDATED.status) throw Exception(response.message())
        return response.body()!!
    }
}