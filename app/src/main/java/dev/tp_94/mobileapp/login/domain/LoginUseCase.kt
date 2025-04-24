package dev.tp_94.mobileapp.login.domain

import dev.tp_94.mobileapp.login.presentation.LoginResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(number: String, password: String): LoginResult {
        if (number.length != 10) {
            return LoginResult.Error("Некорректный формат номера телефона")
        }
        if (password.isEmpty()) {
            return LoginResult.Error("Поле ввода пароля пустое")
        }
        return try {
            val user = userRepository.login(number, password)
            LoginResult.Success(user)
        } catch (e: Exception) {
            LoginResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}