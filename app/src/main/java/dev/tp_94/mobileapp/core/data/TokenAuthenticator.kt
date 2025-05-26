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
        val refreshToken = sessionCache.session?.refreshToken ?: return null

        val newTokens = runBlocking {
            runCatching {
                refreshApi.refreshToken("Bearer $refreshToken")
            }.getOrNull()
        }

        if (newTokens == null) return null

        val cookies = newTokens.headers().values("Set-Cookie")

        val newAccessToken = cookies.find { it.startsWith("accessToken=") }
            ?.substringAfter("accessToken=")
            ?.substringBefore(";")
            ?: throw Exception("Ошибка на стороне сервера! Валидация не прошла успешно")

        val newRefreshToken = cookies.find { it.startsWith("refreshToken=") }
            ?.substringAfter("refreshToken=")
            ?.substringBefore(";")
            ?: throw Exception("Ошибка на стороне сервера! Валидация не прошла успешно")

        sessionCache.updateToken(accessToken = newAccessToken, refreshToken = newRefreshToken)

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newAccessToken")
            .build()
    }
}
