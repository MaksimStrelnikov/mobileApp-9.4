package dev.tp_94.mobileapp.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.metrica.YandexMetrica
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.login.domain.LoginUseCase
import dev.tp_94.mobileapp.login.domain.CheckSessionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionCache: SessionCache,
    private val loginUseCase: LoginUseCase,
    private val revokeUserCase: CheckSessionUseCase
) : ViewModel() {
    fun updatePhoneNumber(phoneNumber: String) {
        _state.value = _state.value.copy(phoneNumber = phoneNumber)
    }

    fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun login(onSuccessCustomer: () -> Unit, onSuccessConfectioner: () -> Unit) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val response = loginUseCase.execute(state.value.phoneNumber, state.value.password)
            if (response is LoginResult.Error) _state.value =
                _state.value.copy(error = response.message)
            else if (response is LoginResult.Success) {
                _state.value = _state.value.copy(error = "")
                YandexMetrica.reportEvent(
                    "login",
                    "{\"screen\":\"login\", \"action\":\"login\", " +
                            "\"type\":\"${if( response.user is Confectioner) "confectioner" else "customer" },}\""
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

    fun validate(onResult: (User?) -> Unit) {
        viewModelScope.launch {
            revokeUserCase.execute(onResult)
        }
    }

    private val _state = MutableStateFlow(
        LoginState()
    )
    val state = _state.asStateFlow()

    val session = sessionCache.session
}