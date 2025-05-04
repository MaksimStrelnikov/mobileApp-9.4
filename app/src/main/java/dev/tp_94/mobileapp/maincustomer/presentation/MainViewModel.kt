package dev.tp_94.mobileapp.maincustomer.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.maincustomer.domain.GetConfectionersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionCache: SessionCache,
    getConfectionersUseCase: GetConfectionersUseCase
) : ViewModel() {
    //TODO: add product request in init
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(
            confectioners = _state.value.confectioners + getConfectionersUseCase.execute(5)
        )
    }

    fun getUser(): User? {
        return sessionCache.session?.user
    }

    fun exit() {
        sessionCache.clearSession()
    }

    fun updateSearchText(it: String) {
        _state.value = _state.value.copy(search = it)
    }

    fun addToBasket(it: CakeGeneral) {
        //TODO
    }
}