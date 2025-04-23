package dev.tp_94.mobileapp.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.profile.domain.ProfileConfectionerChangeUseCase
import dev.tp_94.mobileapp.profile.domain.ProfileCustomerChangeUseCase
import dev.tp_94.mobileapp.signup.presenatation.SignUpState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.annotation.meta.TypeQualifier
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    sessionCache: SessionCache
) : ViewModel() {
    private val _state = MutableStateFlow(
        sessionCache.getActiveSession()
    )

    val state = _state.asStateFlow()
}

@HiltViewModel
class ProfileCustomerViewModel @Inject constructor(
    sessionCache: SessionCache,
    private val profileCustomerChangeUseCase: ProfileCustomerChangeUseCase
) : ViewModel() {

    fun updatePhoneNumber(phoneNumber: String) {
        _state.value = _state.value.copy(phoneNumber = phoneNumber)
    }

    fun updateEmail(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun updateName(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun save() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val response = profileCustomerChangeUseCase.execute(
                phoneNumber = state.value.phoneNumber,
                name = state.value.name,
                email = state.value.email
            )
            if (response is SaveResult.Error) _state.value =
                _state.value.copy(error = response.message)
            else if (response is SaveResult.Success) {
                _state.value = _state.value.copy(error = "")
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    private val _state: MutableStateFlow<ProfileCustomerState>
    init {
        val session = sessionCache.getActiveSession()
            ?: throw IllegalStateException("No active session found")

        val user = session.user
        if (user !is Customer) {
            throw IllegalStateException("Expected Confectioner user, got ${user::class.simpleName}")
        }

        _state = MutableStateFlow(
            ProfileCustomerState(
                isLoading = false,
                name = sessionCache.getActiveSession()!!.user.name,
                phoneNumber = sessionCache.getActiveSession()!!.user.phoneNumber,
                email = sessionCache.getActiveSession()!!.user.email,
                error = "",
            )
        )
    }
    val state = _state.asStateFlow()
}

@HiltViewModel
class ProfileConfectionerViewModel @Inject constructor(
    sessionCache: SessionCache,
    private val profileConfectionerChangeUseCase: ProfileConfectionerChangeUseCase
) : ViewModel() {

    fun updatePhoneNumber(phoneNumber: String) {
        _state.value = _state.value.copy(phoneNumber = phoneNumber)
    }

    fun updateEmail(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun updateName(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun updateAddress(address: String) {
        _state.value = _state.value.copy(address = address)
    }

    fun updateDescription(description: String) {
        _state.value = _state.value.copy(description = description)
    }

    fun save() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val response = profileConfectionerChangeUseCase.execute(
                phoneNumber = state.value.phoneNumber,
                name = state.value.name,
                email = state.value.email,
                description = _state.value.description,
                address = _state.value.address,
            )
            if (response is SaveResult.Error) _state.value =
                _state.value.copy(error = response.message)
            else if (response is SaveResult.Success) {
                _state.value = _state.value.copy(error = "")
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    private val _state: MutableStateFlow<ProfileConfectionerState>
    init {
        val session = sessionCache.getActiveSession()
            ?: throw IllegalStateException("No active session found")

        val user = session.user
        if (user !is Confectioner) {
            throw IllegalStateException("Expected Confectioner user, got ${user::class.simpleName}")
        }

        _state = MutableStateFlow(
            ProfileConfectionerState(
                isLoading = false,
                name = user.name,
                phoneNumber = user.phoneNumber,
                email = user.email,
                description = user.description,
                address = user.address,
                error = ""
            )
        )
    }
    val state = _state.asStateFlow()
}