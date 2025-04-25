package dev.tp_94.mobileapp.profileeditor.presentation

data class ProfileEditorCustomerState(
    val isLoading: Boolean = false,
    val phoneNumber: String = "",
    val name: String = "",
    val email: String = "",
    val error: String? = null
)

data class ProfileEditorConfectionerState(
    val isLoading: Boolean = false,
    val phoneNumber: String = "",
    val name: String = "",
    val email: String = "",
    val address: String = "",
    val description: String = "",
    val error: String? = null
)