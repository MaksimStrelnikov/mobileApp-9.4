package dev.tp_94.mobileapp.add_product.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.add_product.domain.AddProductUseCase
import dev.tp_94.mobileapp.add_product.domain.DeleteProductUseCase
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel  @Inject constructor(
    private val addProductUseCase: AddProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val sessionCache: SessionCache,
) : ViewModel() {

    fun updateName(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun updateDescription(description: String) {
        _state.value = _state.value.copy(description = description)
    }

    fun updateDiameter(diameter: String) {
        _state.value = _state.value.copy(diameter = diameter)
    }

    fun updateWeight(weight: String) {
        _state.value = _state.value.copy(weight = weight)
    }

    fun updateWorkPeriod(workPeriod: String) {
        _state.value = _state.value.copy(workPeriod = workPeriod)
    }

    fun updatePrice(price: String) {
        _state.value = _state.value.copy(price = price)
    }

    fun updateImage(image: String?) {
        _state.value = _state.value.copy(image = image)
    }

    fun delete(onMove: () -> Unit) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val response = deleteProductUseCase.execute(
                name = state.value.name,
                description = state.value.description,
                diameter = state.value.diameter,
                weight = state.value.weight,
                workPeriod = state.value.workPeriod,
                price = state.value.price,
                imageUrl = state.value.image,
                confectioner = (getUser() as Confectioner)
            )
            if (response is AddProductResult.Error) _state.value =
                _state.value.copy(error = response.message)
            else if (response is AddProductResult.Success) {
                _state.value = _state.value.copy(error = "")
                onMove()
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun save(onMove: () -> Unit) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val response = addProductUseCase.execute(
                name = state.value.name,
                description = state.value.description,
                diameter = state.value.diameter,
                weight = state.value.weight,
                workPeriod = state.value.workPeriod,
                price = state.value.price,
                imageUrl = state.value.image,
                confectioner = (getUser() as Confectioner)
            )

            if (response is AddProductResult.Error) _state.value =
                _state.value.copy(error = response.message)
            else if (response is AddProductResult.Success) {
                _state.value = _state.value.copy(error = "")
                onMove()
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun getUser(): User? {
        return sessionCache.session?.user
    }

    private val _state = MutableStateFlow(
        AddProductState(
            name = "Торт",
            description = "Это точно торт",
            diameter = "22",
            weight = "2",
            workPeriod = "2",
            price = "12000",
            image = null
    ))
    val state = _state.asStateFlow()
}