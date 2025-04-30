package dev.tp_94.mobileapp.customersfeed.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.customersfeed.domain.LoadMoreConfectionersUseCase
import dev.tp_94.mobileapp.customersfeed.domain.SearchForConfectionersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersFeedViewModel @Inject constructor(
    private val sessionCache: SessionCache,
    private val loadMoreConfectionersUseCase: LoadMoreConfectionersUseCase,
    private val searchForConfectionersUseCase: SearchForConfectionersUseCase
): ViewModel() {
    private val _state = MutableStateFlow((CustomersFeedState()))
    val state = _state.asStateFlow()

    fun getUser(): User? {
        return sessionCache.session?.user
    }

    fun updateSearchText(text: String) {
        _state.value = _state.value.copy(searchText = text)
    }

    fun search() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            searchForConfectionersUseCase.execute(state.value.searchText)
        }
        _state.value = _state.value.copy(isLoading = false)
    }

    fun loadMore() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {

        }
        _state.value = _state.value.copy(isLoading = false)
    }
}