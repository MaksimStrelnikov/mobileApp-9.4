package dev.tp_94.mobileapp.customers_feed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.customers_feed.domain.LoadMoreConfectionersUseCase
import dev.tp_94.mobileapp.customers_feed.domain.SearchForConfectionersUseCase
import dev.tp_94.mobileapp.customers_feed.domain.SortConfectionersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersFeedViewModel @Inject constructor(
    private val sessionCache: SessionCache,
    private val loadMoreConfectionersUseCase: LoadMoreConfectionersUseCase,
    private val searchForConfectionersUseCase: SearchForConfectionersUseCase,
    private val sortConfectionersUseCase: SortConfectionersUseCase
) : ViewModel() {
    private val _state = MutableStateFlow((CustomersFeedState()))
    val state = _state.asStateFlow()

    init {
        search()
    }

    val session = sessionCache.session

    fun exit() {
        sessionCache.clearSession()
    }

    fun updateSearchText(text: String) {
        _state.value = _state.value.copy(searchText = text)
    }

    fun search() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val result = searchForConfectionersUseCase.execute(state.value.searchText)
            if (result is FeedResult.Success) {
                _state.value = _state.value.copy(feed = result.confectioners)
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = loadMoreConfectionersUseCase.execute(state.value.searchText)
            if (result is FeedResult.Success) {
                _state.value = _state.value.copy(feed = _state.value.feed + result.confectioners)
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun selectSort(sorting: Sorting) {
        _state.value = _state.value.copy(currentSorting = sorting)
        _state.value = _state.value.copy(feed = emptyList())
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = sortConfectionersUseCase.execute(state.value.searchText, sorting)
            if (result is FeedResult.Success) {
                _state.value = _state.value.copy(feed = result.confectioners)
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }
}