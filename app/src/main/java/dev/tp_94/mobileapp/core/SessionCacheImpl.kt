package dev.tp_94.mobileapp.core

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.core.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SessionCacheImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SessionCache {

    private val _session = MutableStateFlow<Session?>(null);
    override val session: StateFlow<Session?>
        get() = _session.asStateFlow()

    private val moshi = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(User::class.java, "type")
                .withSubtype(Customer::class.java, "customer")
                .withSubtype(Confectioner::class.java, "confectioner")
        )
        .add(KotlinJsonAdapterFactory())
        .build()
    private val adapter = moshi.adapter(Session::class.java)

    init {
        val json = sharedPreferences.getString("session", null)
        _session.update {
            try {
                json?.let { adapter.fromJson(it) }
            } catch (e: Exception) {
                null
            }
        }
    }


    @Synchronized
    override fun saveSession(session: Session) {
        sharedPreferences.edit()
            .putString("session", adapter.toJson(session))
            .apply()
        _session.value = session
    }

    @Synchronized
    override fun updateUser(user: User) {
        _session.update { old ->
            old?.copy(user = user)
        }
    }

    @Synchronized
    override fun clearSession() {
        sharedPreferences.edit().remove("session").apply()
        _session.update { _ ->
            null
        }
    }

    @Synchronized
    override fun updateToken(accessToken: String, refreshToken: String) {
        _session.update { old ->
            old?.copy(accessToken = accessToken, refreshToken = refreshToken)
        }
    }
}