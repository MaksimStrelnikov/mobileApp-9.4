package dev.tp_94.mobileapp.signup.presenatation

data class SignUpState(
    val isLoading: Boolean = false,
    val phoneNumber: String = "",
    val password: String = "",
    val name: String = "",
    val email: String = "",
    val address: String = "",
    val isConfectioner: Boolean = false,
    val error: String? = null
)
