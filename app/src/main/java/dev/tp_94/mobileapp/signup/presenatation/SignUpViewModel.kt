package dev.tp_94.mobileapp.signup.presenatation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.signup.domain.SignUpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase) : ViewModel() {
    fun updatePhoneNumber(phoneNumber: String) {
        _state.value = _state.value.copy(phoneNumber = phoneNumber)
    }

    fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun updateEmail(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun updateName(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun signUp(onSuccess: (User) -> Unit) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val response = signUpUseCase.execute(
                state.value.phoneNumber, state.value.password,
                name = state.value.name,
                email = state.value.email
            )
            if (response is SignUpState.Error) _state.value =
                _state.value.copy(error = response.message)
            else if (response is SignUpState.Success) {
                _state.value = _state.value.copy(error = "")
                onSuccess(response.user)
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    private val _state = MutableStateFlow(
        ScreenState()
    )
    val state = _state.asStateFlow()
}