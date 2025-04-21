package dev.tp_94.mobileapp.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.login.domain.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    fun updatePhoneNumber(phoneNumber: String) {
        _state.value = _state.value.copy(phoneNumber = phoneNumber)
    }

    fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun login() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val response = loginUseCase.execute(state.value.phoneNumber, state.value.password)
            if (response is LoginState.Error) _state.value = _state.value.copy(error = response.message)
            else _state.value = _state.value.copy(error = "")
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    private val _state = MutableStateFlow(
        ScreenState()
    )
    val state = _state.asStateFlow()
}