package dev.tp_94.mobileapp.add_product.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.add_product.domain.AddProductUseCase
import dev.tp_94.mobileapp.add_product.domain.DeleteProductUseCase
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.CakeSerializerModule
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel  @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addProductUseCase: AddProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val sessionCache: SessionCache,
) : ViewModel() {

    private val _state = MutableStateFlow(
        AddProductState(
            savedStateHandle.get<String>("cake")
                ?.let { URLDecoder.decode(it, "UTF-8") }
                ?.let { Json { serializersModule = CakeSerializerModule.module }.decodeFromString<CakeGeneral>(it) }
                ?: CakeGeneral(confectioner = (getUser() as Confectioner)),
        )
    )

    val state = _state.asStateFlow()

    fun updateName(name: String) {
        _state.value = _state.value.copy(cakeGeneral = _state.value.cakeGeneral.copy(name = name))
    }

    fun updateDescription(description: String) {
        _state.value = _state.value.copy(cakeGeneral = _state.value.cakeGeneral.copy(description = description))
    }

    fun updateDiameter(diameter: String) {
        _state.value = _state.value.copy(cakeGeneral = _state.value.cakeGeneral.copy(diameter = diameter.toFloat()))
    }

    fun updateWeight(weight: String) {
        _state.value = _state.value.copy(cakeGeneral = _state.value.cakeGeneral.copy(weight = weight.toFloat()))
    }

    fun updateWorkPeriod(workPeriod: String) {
        _state.value = _state.value.copy(cakeGeneral = _state.value.cakeGeneral.copy(preparation = workPeriod.toInt()))
    }

    fun updatePrice(price: String) {
        _state.value = _state.value.copy(cakeGeneral = _state.value.cakeGeneral.copy(price = price.toInt()))
    }

    fun updateImage(image: String?) {
        _state.value = _state.value.copy(cakeGeneral = _state.value.cakeGeneral.copy(imageUrl = image))
    }

    fun delete(onMove: () -> Unit) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val response = deleteProductUseCase.execute(
                cake = state.value.cakeGeneral
            )
            if (response is ProductResult.Error) _state.value =
                _state.value.copy(error = response.message)
            else if (response is ProductResult.Success) {
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
                cake = state.value.cakeGeneral
            )
            if (response is ProductResult.Error) _state.value =
                _state.value.copy(error = response.message)
            else if (response is ProductResult.Success) {
                _state.value = _state.value.copy(error = "")
                onMove()
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun getUser(): User? {
        return sessionCache.session?.user
    }
}