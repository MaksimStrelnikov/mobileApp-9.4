package dev.tp_94.mobileapp.login.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.core.models.toConfectioner
import dev.tp_94.mobileapp.core.models.toCustomer
import javax.inject.Inject

class CheckSessionUseCase @Inject constructor(
    private val repository: UserRepository,
    private val sessionCache: SessionCache
    ) {
    suspend fun execute(onResult: (User?) -> Unit): Boolean {
        try {
            val result = repository.getCurrent()
            when (result.type) {
                "confectioner" -> {
                    sessionCache.updateUser(result.toConfectioner())
                    onResult(sessionCache.session.value?.user)
                }
                "customer" -> {
                    sessionCache.updateUser(result.toCustomer())
                    onResult(sessionCache.session.value?.user)
                }
                else -> {
                    throw Exception("Unknown user type")
                }
            }
            return true
        } catch (e: Exception) {
            sessionCache.clearSession()
            onResult(sessionCache.session.value?.user)
            return false
        }
    }
}
