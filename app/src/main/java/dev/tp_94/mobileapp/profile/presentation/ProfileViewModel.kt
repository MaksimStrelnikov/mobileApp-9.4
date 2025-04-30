package dev.tp_94.mobileapp.profile.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.User
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val sessionCache: SessionCache) : ViewModel() {
    fun getUser(): User? {
        return sessionCache.session?.user
    }

    fun logout() {
        sessionCache.clearSession()
    }
}