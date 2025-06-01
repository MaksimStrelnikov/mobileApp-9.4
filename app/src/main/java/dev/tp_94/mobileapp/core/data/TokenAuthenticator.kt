package dev.tp_94.mobileapp.core.data

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.api.RefreshApi
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val refreshApi: RefreshApi,
    private val sessionCache: SessionCache
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = sessionCache.session.value?.refreshToken ?: return null

        val newTokens = runBlocking {
            runCatching {
                refreshApi.refreshToken("Bearer $refreshToken")
            }.getOrNull()
        }

        if (newTokens == null || !newTokens.isSuccessful || newTokens.body() == null) {
            return null
        }

        sessionCache.updateToken(accessToken = newTokens.body()!!.accessToken, refreshToken = newTokens.body()!!.refreshToken)

        return response.request.newBuilder()
            .header("Authorization", "Bearer ${newTokens.body()!!.accessToken}")
            .build()
    }
}
