package dev.tp_94.mobileapp.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.profile.domain.DeleteAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionCache: SessionCache,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        ProfileState()
    )
    val state = _state.asStateFlow()

    fun getUser(): User? {
        return sessionCache.session?.user
    }

    fun logout() {
        sessionCache.clearSession()

    }

    fun deleteAccount(onLogout: () -> Unit) {
        viewModelScope.launch {
            val response = deleteAccountUseCase.execute()
            if (response is DeleteAccountResult.Error)
                _state.value = _state.value.copy(error = response.message)
            else if (response is DeleteAccountResult.Success) {
                _state.value = _state.value.copy(error = "")
                onLogout()
            }
        }
    }

    fun deleteAccount() {
        // TODO: delete account
    }
}