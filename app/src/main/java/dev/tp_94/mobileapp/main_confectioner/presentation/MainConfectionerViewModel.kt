package dev.tp_94.mobileapp.main_confectioner.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainConfectionerViewModel @Inject constructor(private val sessionCache: SessionCache) :
    ViewModel() {
    fun getUser(): User? {
        return sessionCache.session?.user
    }

    fun exit() {
        sessionCache.clearSession()
    }

    private val _state = MutableStateFlow(
        MainConfectionerState(
            getUser()!! as Confectioner
        )
    )

    val state = _state.asStateFlow()
}