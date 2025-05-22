package dev.tp_94.mobileapp.login.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.login.presentation.LoginResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionCache: SessionCache
) {
    suspend fun execute(number: String, password: String): LoginResult {
        if (number.length != 10) {
            return LoginResult.Error("Некорректный формат номера телефона")
        }
        if (password.isEmpty()) {
            return LoginResult.Error("Поле ввода пароля пустое")
        }
        return try {
            val session = userRepository.login(number, password)
            sessionCache.saveSession(session)
            LoginResult.Success(session.user)
        } catch (e: Exception) {
            LoginResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}