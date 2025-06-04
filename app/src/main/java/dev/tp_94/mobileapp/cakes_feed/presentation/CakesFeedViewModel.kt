package dev.tp_94.mobileapp.cakes_feed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.cakes_feed.domain.LoadMoreCakesUseCase
import dev.tp_94.mobileapp.cakes_feed.domain.SearchForCakesUseCase
import dev.tp_94.mobileapp.confectioner_page.domain.AddToBasketUseCase
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Cake
import dev.tp_94.mobileapp.core.models.CakeGeneral
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CakesFeedViewModel @Inject constructor(
    sessionCache: SessionCache,
    private val loadMoreCakesUseCase: LoadMoreCakesUseCase,
    private val searchForCakesUseCase: SearchForCakesUseCase,
    private val addToBasketUseCase: AddToBasketUseCase
) : ViewModel() {
    private val _state = MutableStateFlow((CakesFeedState()))
    val state = _state.asStateFlow()

    val session = sessionCache.session

    fun updateSearchText(text: String) {
        _state.value = _state.value.copy(searchText = text)
    }
    fun search() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val result = searchForCakesUseCase.execute(state.value.searchText, state.value.currentSorting)
            if (result is CakeFeedResult.Success) {
                _state.value = _state.value.copy(feed = result.data)
            }
            //TODO: add error message
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = loadMoreCakesUseCase.execute(state.value.searchText)
            if (result is CakeFeedResult.Success) {
                _state.value = _state.value.copy(feed = _state.value.feed + result.data)
            }
            //TODO: add error message
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun buy(cake: Cake) {
        viewModelScope.launch {
           val response = addToBasketUseCase.execute(
                    cake = cake as CakeGeneral
           )
            //TODO: add error message
        }
    }

    fun selectSort(sorting: Sorting) {
        _state.value = _state.value.copy(currentSorting = sorting, feed = emptyList())
        search()
    }

    init {
        search()
    }
}