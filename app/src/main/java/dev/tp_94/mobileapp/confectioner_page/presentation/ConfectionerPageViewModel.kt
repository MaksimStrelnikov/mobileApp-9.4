package dev.tp_94.mobileapp.confectioner_page.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class ConfectionerPageViewModel @Inject constructor(savedStateHandle: SavedStateHandle, private val sessionCache: SessionCache) : ViewModel() {
    private val _state = MutableStateFlow(
        ConfectionerPageState(
            savedStateHandle.get<String>("confectionerJson")
                ?.let { URLDecoder.decode(it, "UTF-8") }
                ?.let { Json.decodeFromString<Confectioner>(it) }
                ?: error("Confectioner not provided")
        )
    )
    val state = _state.asStateFlow()

    fun getUser(): User? {
        return sessionCache.session?.user
    }

    fun exit() {
        return sessionCache.clearSession()
    }
}