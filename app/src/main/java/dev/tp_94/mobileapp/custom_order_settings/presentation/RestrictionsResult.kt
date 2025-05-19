package dev.tp_94.mobileapp.custom_order_settings.presentation

import dev.tp_94.mobileapp.core.models.Restrictions

sealed class RestrictionsResult {
    data class Success(val restrictions: Restrictions) : RestrictionsResult()
    data class Error(val message: String) : RestrictionsResult()
}
