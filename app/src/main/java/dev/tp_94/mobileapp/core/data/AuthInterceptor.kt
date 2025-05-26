package dev.tp_94.mobileapp.core.data

import dev.tp_94.mobileapp.core.SessionCache
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionCache: SessionCache
) : Interceptor {

    @Synchronized
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sessionCache.session?.accessToken

        val request = chain.request().newBuilder()
            .apply {
                token?.let {
                    header("Authorization", "Bearer $it")
                }
            }.build()
        return chain.proceed(request)
    }
}