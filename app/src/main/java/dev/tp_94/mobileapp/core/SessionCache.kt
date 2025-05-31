package dev.tp_94.mobileapp.core

import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.core.models.User
import kotlinx.coroutines.flow.StateFlow

interface SessionCache {
    val session: StateFlow<Session?>

    fun saveSession(session: Session)

    fun updateUser(user: User)

    fun clearSession()

    fun updateToken(accessToken: String, refreshToken: String)
}