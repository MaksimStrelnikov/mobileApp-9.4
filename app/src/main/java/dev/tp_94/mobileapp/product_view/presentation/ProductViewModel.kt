package dev.tp_94.mobileapp.product_view.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Cake
import dev.tp_94.mobileapp.core.models.CakeSerializerModule
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sessionCache: SessionCache
) : ViewModel() {
    private val json = Json { serializersModule = CakeSerializerModule.module }


    private val _state = MutableStateFlow(
        ProductState(
            cake = savedStateHandle.get<String>("cake")
                ?.let { URLDecoder.decode(it, "UTF-8") }
                ?.let { json.decodeFromString<Cake>(it) }
                ?: error("Cake not provided"),
            price = savedStateHandle.get<Int>("price") ?: 0,
        )
    )
    val state = _state.asStateFlow()

    fun getUser(): User? {
        return sessionCache.session?.user
    }

    fun exit() {
        sessionCache.clearSession()
    }
}