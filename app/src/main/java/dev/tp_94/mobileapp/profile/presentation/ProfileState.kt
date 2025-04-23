package dev.tp_94.mobileapp.profile.presentation

import dev.tp_94.mobileapp.core.models.Customer

data class ProfileCustomerState(
    val isLoading: Boolean = false,
    val phoneNumber: String = "",
    val name: String = "",
    val email: String = "",
    val error: String? = null
)

data class ProfileConfectionerState(
    val isLoading: Boolean = false,
    val phoneNumber: String = "",
    val name: String = "",
    val email: String = "",
    val address: String = "",
    val description: String = "",
    val error: String? = null
)