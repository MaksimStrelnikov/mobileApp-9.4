package dev.tp_94.mobileapp.profile.presentation


sealed class DeleteAccountResult {
    data object Success : DeleteAccountResult()
    data class Error(val message: String) : DeleteAccountResult()
}