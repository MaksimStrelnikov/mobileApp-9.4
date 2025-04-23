package dev.tp_94.mobileapp.signup.domain

import android.util.Patterns
import dev.tp_94.mobileapp.core.models.CustomerPassword
import dev.tp_94.mobileapp.login.domain.UserRepository
import dev.tp_94.mobileapp.signup.presenatation.SignUpState
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(phoneNumber: String, password: String, name: String, email: String): SignUpState {
        if (!(phoneNumber.matches(Regex("[0-9]*")) && phoneNumber.length == 10)) {
            return SignUpState.Error("Некорректный формат номера телефона")
        }
        if (password.isEmpty()) {
            return SignUpState.Error("Пароль не указан")
        }
        if (name.isEmpty()) {
            return SignUpState.Error("Имя не указано")
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return SignUpState.Error("Некорректный формат адреса электронной почты")
        }
        try {
            val user = userRepository.add(
                CustomerPassword(
                    id = 0,
                    name = name,
                    phoneNumber = phoneNumber,
                    email = email,
                    password = password
                )
            )
            return SignUpState.Success(user)
        } catch (e: Exception) {
            return SignUpState.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}