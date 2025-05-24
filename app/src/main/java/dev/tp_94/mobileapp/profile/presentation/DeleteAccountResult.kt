package dev.tp_94.mobileapp.profile.presentation

import dev.tp_94.mobileapp.core.models.User

sealed class DeleteAccountResult {
    data class Success(val user: User) : DeleteAccountResult()
    data class Error(val message: String) : DeleteAccountResult()
}