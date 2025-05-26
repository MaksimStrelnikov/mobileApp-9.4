package dev.tp_94.mobileapp.self_made_cake.domain

import dev.tp_94.mobileapp.custom_order_settings.data.RestrictionsRequestDTO
import dev.tp_94.mobileapp.custom_order_settings.data.RestrictionsResponseDTO

interface RestrictionsRepository {
    suspend fun getCustomCakeRestrictions(id: Long): RestrictionsResponseDTO
    suspend fun updateCustomCakeRestrictions(confectionerId: Long, restrictions: RestrictionsRequestDTO): RestrictionsResponseDTO
}