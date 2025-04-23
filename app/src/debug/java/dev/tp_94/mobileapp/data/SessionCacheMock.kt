package dev.tp_94.mobileapp.data

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.core.models.User
import javax.inject.Inject

class SessionCacheMock @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SessionCache {

    private val moshi = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(User::class.java, "type")
                .withSubtype(Customer::class.java, "customer")
                .withSubtype(Confectioner::class.java, "confectioner")
        )
        .add(KotlinJsonAdapterFactory())
        .build()
    private val adapter = moshi.adapter(Session::class.java)

    override fun saveSession(session: Session) {
        sharedPreferences.edit()
            .putString("session", adapter.toJson(session))
            .apply()
    }

    override fun getActiveSession(): Session? {
        val json = sharedPreferences.getString("session", null) ?: return null
        return adapter.fromJson(json)
    }

    override fun clearSession() {
        sharedPreferences.edit().remove("session").apply()
    }
}