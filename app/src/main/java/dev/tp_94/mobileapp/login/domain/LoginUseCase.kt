package dev.tp_94.mobileapp.login.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.core.models.toConfectioner
import dev.tp_94.mobileapp.core.models.toCustomer
import dev.tp_94.mobileapp.login.data.dto.UserLoginDTO
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
            val dto = userRepository.login(UserLoginDTO(number, password))
            val user: User
            if (dto.type == "confectioner") {
                user = dto.toConfectioner()
            } else if (dto.type == "customer") {
                user = dto.toCustomer()
            } else {
                return LoginResult.Error("Пользователь не может быть авторизован")
            }
            sessionCache.saveSession(Session(user, "token")) //TODO tokenization BACKEND AWAITING
            return LoginResult.Success(user)
        } catch (e: Exception) {
            LoginResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}