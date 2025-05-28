package dev.tp_94.mobileapp.profile.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.login.domain.UserRepository
import dev.tp_94.mobileapp.profile.presentation.DeleteAccountResult
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionCache: SessionCache
) {
    suspend fun execute(): DeleteAccountResult {
        try {
            userRepository.delete()
            sessionCache.clearSession()
            return DeleteAccountResult.Success
        } catch (e: Exception) {
            return DeleteAccountResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}