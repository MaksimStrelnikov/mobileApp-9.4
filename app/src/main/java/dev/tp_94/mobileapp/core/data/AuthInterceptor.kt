package dev.tp_94.mobileapp.core.data

import android.util.Log
import dev.tp_94.mobileapp.core.SessionCache
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionCache: SessionCache
) : Interceptor {

    @Synchronized
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sessionCache.session.value?.accessToken

        val request = chain.request().newBuilder()
            .apply {
                token?.let {
                    header("Authorization", "Bearer $it")
                }
            }.build()

        Log.println(Log.INFO, "Log", request.header("Authorization").toString())
        return chain.proceed(request)
    }
}