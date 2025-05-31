package dev.tp_94.mobileapp.custom_order_settings.presentation

import dev.tp_94.mobileapp.core.models.User

sealed class CustomSettingsResult {
    data class Success(val user: User) : CustomSettingsResult()
    data class Error(val message: String) : CustomSettingsResult()
}