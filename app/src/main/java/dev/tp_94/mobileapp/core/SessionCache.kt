package dev.tp_94.mobileapp.core

import dev.tp_94.mobileapp.core.models.Session

interface SessionCache {

    fun saveSession(session: Session)

    fun getActiveSession() : Session?

    fun clearSession()
}