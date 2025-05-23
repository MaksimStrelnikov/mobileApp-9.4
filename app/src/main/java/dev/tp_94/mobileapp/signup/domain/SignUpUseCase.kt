package dev.tp_94.mobileapp.signup.domain

import android.util.Patterns
import dev.tp_94.mobileapp.core.models.ConfectionerPassword
import dev.tp_94.mobileapp.core.models.CustomerPassword
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.login.domain.UserRepository
import dev.tp_94.mobileapp.signup.presenatation.SignUpResult
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(
        phoneNumber: String,
        password: String,
        name: String,
        email: String,
        address: String,
        isConfectioner: Boolean
    ): SignUpResult {
        if (!(phoneNumber.matches(Regex("[0-9]*")) && phoneNumber.length == 10)) {
            return SignUpResult.Error("Некорректный формат номера телефона")
        }
        if (password.isEmpty()) {
            return SignUpResult.Error("Пароль не указан")
        }
        if (name.isEmpty()) {
            return SignUpResult.Error("Имя не указано")
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return SignUpResult.Error("Некорректный формат адреса электронной почты")
        }
        try {
            val user: User
            if (isConfectioner) {
                if (address.isEmpty()) {
                    return SignUpResult.Error("Адрес не указан")
                }
                user = userRepository.add(
                    ConfectionerPassword(
                        id = 0,
                        name = name,
                        phoneNumber = phoneNumber,
                        email = email,
                        password = password,
                        description = "",
                        address = address
                    )
                )
            } else {
                user = userRepository.add(
                    CustomerPassword(
                        id = 0,
                        name = name,
                        phoneNumber = phoneNumber,
                        email = email,
                        password = password
                    )
                )
            }
            return SignUpResult.Success(user)
        } catch (e: Exception) {
            return SignUpResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}