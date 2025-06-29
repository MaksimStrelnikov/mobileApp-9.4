package dev.tp_94.mobileapp.profile_editor.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.profile_editor.domain.ConfectionerProfileChangeUseCase
import dev.tp_94.mobileapp.profile_editor.domain.CustomerEditorChangeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditorViewModel @Inject constructor(
    sessionCache: SessionCache
) : ViewModel() {
    private val _state = MutableStateFlow(
        sessionCache.session.value?.user
    )

    val state = _state.asStateFlow()
}

@HiltViewModel
class ProfileEditorCustomerViewModel @Inject constructor(
    sessionCache: SessionCache,
    private val customerEditorChangeUseCase: CustomerEditorChangeUseCase
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

    fun save(onSuccess: () -> Unit) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val response = customerEditorChangeUseCase.execute(
                phoneNumber = state.value.phoneNumber,
                name = state.value.name,
                email = state.value.email
            )
            if (response is SaveResult.Error) _state.value =
                _state.value.copy(error = response.message)
            else if (response is SaveResult.Success) {
                _state.value = _state.value.copy(error = "")
                onSuccess()
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    private val _state: MutableStateFlow<ProfileEditorCustomerState>
    init {
        val session = sessionCache.session.value
            ?: throw IllegalStateException("No active session found")

        val user = session.user
        if (user !is Customer) {
            throw IllegalStateException("Expected Customer user, got ${user::class.simpleName}")
        }

        _state = MutableStateFlow(
            ProfileEditorCustomerState(
                isLoading = false,
                name = user.name,
                phoneNumber = user.phoneNumber,
                email = user.email,
                error = "",
            )
        )
    }
    val state = _state.asStateFlow()
}

@HiltViewModel
class ProfileEditorConfectionerViewModel @Inject constructor(
    sessionCache: SessionCache,
    private val confectionerProfileChangeUseCase: ConfectionerProfileChangeUseCase
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

    fun save(onSuccess: () -> Unit) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val response = confectionerProfileChangeUseCase.execute(
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
                onSuccess()
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    private val _state: MutableStateFlow<ProfileEditorConfectionerState>
    init {
        val session = sessionCache.session.value
            ?: throw IllegalStateException("No active session found")

        val user = session.user
        if (user !is Confectioner) {
            throw IllegalStateException("Expected Confectioner user, got ${user::class.simpleName}")
        }

        _state = MutableStateFlow(
            ProfileEditorConfectionerState(
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