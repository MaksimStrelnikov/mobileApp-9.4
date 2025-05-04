package dev.tp_94.mobileapp.cakesfeed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.cakesfeed.domain.LoadMoreCakesUseCase
import dev.tp_94.mobileapp.cakesfeed.domain.SearchForCakesUseCase
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Cake
import dev.tp_94.mobileapp.core.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CakesFeedViewModel @Inject constructor(
    private val sessionCache: SessionCache,
    private val loadMoreCakesUseCase: LoadMoreCakesUseCase,
    private val searchForCakesUseCase: SearchForCakesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow((CakesFeedState()))
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
            searchForCakesUseCase.execute(state.value.searchText)
        }
        _state.value = _state.value.copy(isLoading = false)
    }

    fun loadMore() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            loadMoreCakesUseCase.execute(state.value.searchText)
        }
        _state.value = _state.value.copy(isLoading = false)
    }

    fun buy(cake: Cake) {
        //TODO
    }

    fun selectSort(sorting: Sorting) {
        _state.value = _state.value.copy(currentSorting = sorting)
    }
}