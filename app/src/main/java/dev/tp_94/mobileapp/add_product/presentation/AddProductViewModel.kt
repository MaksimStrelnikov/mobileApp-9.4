package dev.tp_94.mobileapp.add_product.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
    sessionCache: SessionCache,
) : ViewModel() {

    private val initialCake = savedStateHandle.get<String>("cake")
        ?.let { URLDecoder.decode(it, "UTF-8") }
        ?.let { Json { serializersModule = CakeSerializerModule.module }.decodeFromString<CakeGeneral>(it) }

    val session = sessionCache.session
    private val _state = MutableStateFlow(
        AddProductState(
            cakeGeneral = initialCake ?: CakeGeneral(confectioner = (session.value!!.user as Confectioner)),
            isEditing = initialCake != null
        )
    )

    val state = _state.asStateFlow()

    fun updateName(name: String) {
        _state.value = _state.value.copy(cakeGeneral = _state.value.cakeGeneral.copy(name = name))
    }

    fun updateDescription(description: String) {
        _state.value = _state.value.copy(cakeGeneral = _state.value.cakeGeneral.copy(description = description))
    }

    fun updateDiameter(diameter: String?) {
        _state.value = _state.value.copy(cakeGeneral = _state.value.cakeGeneral.copy(diameter = diameter?.trim()?.takeIf { it.isNotEmpty() }?.toFloat() ?: 0f))
    }

    fun updateWeight(weight: String?) {
        _state.value = _state.value.copy(cakeGeneral = _state.value.cakeGeneral.copy(weight = weight?.trim()?.takeIf { it.isNotEmpty() }?.toFloat() ?: 0f))
    }

    fun updateWorkPeriod(workPeriod: String?) {
        _state.value = _state.value.copy(cakeGeneral = _state.value.cakeGeneral.copy(preparation = workPeriod?.trim()?.takeIf { it.isNotEmpty() }?.toInt() ?: 0))
    }

    fun updatePrice(price: String?) {
        _state.value = _state.value.copy(cakeGeneral = _state.value.cakeGeneral.copy(price = price?.trim()?.takeIf { it.isNotEmpty() }?.toInt() ?: 0))
    }

    fun updateImage(image: String?) {
        _state.value = _state.value.copy(cakeGeneral = _state.value.cakeGeneral.copy(imageUrl = image))
    }

    fun changeDialogStatus() {
        _state.value = _state.value.copy(isDialogOpen = !_state.value.isDialogOpen)
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
                cake = state.value.cakeGeneral, isUpdate = state.value.isEditing
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
}