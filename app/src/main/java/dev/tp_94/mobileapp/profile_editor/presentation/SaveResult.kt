package dev.tp_94.mobileapp.profile_editor.presentation

import dev.tp_94.mobileapp.core.models.User

sealed class SaveResult {
    data class Success(val user: User) : SaveResult()
    data class Error(val message: String) : SaveResult()
}