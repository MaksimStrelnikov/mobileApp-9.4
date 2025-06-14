package dev.tp_94.mobileapp.signup.presenatation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.metrica.YandexMetrica
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.models.Confectioner
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

    fun updateConfectioner(isConfectioner: Boolean) {
        _state.value = _state.value.copy(isConfectioner = isConfectioner)
    }

    fun updateAddress(address: String) {
        _state.value = _state.value.copy(address = address)
    }

    fun signUp(onSuccessCustomer: () -> Unit, onSuccessConfectioner: () -> Unit) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val response = signUpUseCase.execute(
                phoneNumber = state.value.phoneNumber,
                password = state.value.password,
                name = state.value.name,
                email = state.value.email,
                address = state.value.address,
                isConfectioner = state.value.isConfectioner
            )
            if (response is SignUpResult.Error) _state.value =
                _state.value.copy(error = response.message)
            else if (response is SignUpResult.Success) {
                _state.value = _state.value.copy(error = "")
                YandexMetrica.reportEvent(
                    "sign_up",
                    "{\"screen\":\"sign_up\", \"action\":\"sign_up\", " +
                            "\"type\":\"${if( response.user is Confectioner) "confectioner" else "customer" }}\""
                )
                if (response.user is Confectioner) {
                    onSuccessConfectioner()
                } else {
                    onSuccessCustomer()
                }
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    private val _state = MutableStateFlow(
        SignUpState()
    )
    val state = _state.asStateFlow()
}