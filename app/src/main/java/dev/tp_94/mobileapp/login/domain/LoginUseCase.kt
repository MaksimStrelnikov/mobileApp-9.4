package dev.tp_94.mobileapp.login.domain

import dev.tp_94.mobileapp.login.presentation.LoginState
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(number: String, password: String): LoginState {
        if (number.length != 10) {
            return LoginState.Error("Некорректный формат номера телефона")
        }
        if (password.isEmpty()) {
            return LoginState.Error("Поле ввода пароля пустое")
        }
        return try {
            val user = userRepository.login(number, password)
            LoginState.Success(user)
        } catch (e: Exception) {
            LoginState.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}