package dev.tp_94.mobileapp.core

import dev.tp_94.mobileapp.core.models.Session

interface SessionCache {
    val session: Session?

    fun saveSession(session: Session)

    fun clearSession()
}