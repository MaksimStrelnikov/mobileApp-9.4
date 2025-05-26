package dev.tp_94.mobileapp.core

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.core.models.User
import javax.inject.Inject

class SessionCacheImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SessionCache {

    override var session: Session? = null

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
        session = json?.let { adapter.fromJson(it) }
    }


    @Synchronized
    override fun saveSession(session: Session) {
        sharedPreferences.edit()
            .putString("session", adapter.toJson(session))
            .apply()
        this.session = session
    }

    @Synchronized
    override fun updateUser(user: User) {
        if (session != null) {
            session = session!!.copy(user = user)
        }
    }

    @Synchronized
    override fun clearSession() {
        sharedPreferences.edit().remove("session").apply()
        this.session = null
    }

    @Synchronized
    override fun updateToken(accessToken: String, refreshToken: String) {
        if (session != null) {
            session = session!!.copy(accessToken = accessToken, refreshToken = refreshToken)
        }
    }
}