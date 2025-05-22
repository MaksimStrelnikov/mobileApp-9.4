package dev.tp_94.mobileapp.self_made_cake.domain

import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Restrictions

interface RestrictionRepository {
    suspend fun getCustomCakeRestrictions(confectioner: Confectioner): Restrictions
    suspend fun updateCustomCakeRestrictions(restrictions: Restrictions): Restrictions
}