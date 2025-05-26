package dev.tp_94.mobileapp.core.data

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.api.AuthApi
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val authApi: AuthApi,
    private val sessionCache: SessionCache
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = sessionCache.session?.refreshToken ?: return null

        val newTokens = runBlocking {
            runCatching {
                authApi.refreshToken("Bearer $refreshToken").body()
            }.getOrNull()
        } ?: return null

        sessionCache.updateToken(accessToken = newTokens.accessToken, refreshToken = newTokens.refreshToken)

        return response.request.newBuilder()
            .header("Authorization", "Bearer ${newTokens.accessToken}")
            .build()
    }
}
