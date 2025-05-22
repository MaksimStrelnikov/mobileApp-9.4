package dev.tp_94.mobileapp.signup.domain

import android.util.Patterns
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.login.domain.UserRepository
import dev.tp_94.mobileapp.signup.presenatation.SignUpResult
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionCache: SessionCache
) {
    suspend fun execute(
        phoneNumber: String,
        password: String,
        name: String,
        email: String,
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
            val session: Session
            if (isConfectioner) {
                session = userRepository.add(
                    Confectioner(
                        name = name,
                        phoneNumber = phoneNumber,
                        email = email,
                        description = "",
                        address = "",
                        id = 0,
                        canWithdrawal = 0,
                        inProcess = 0
                    ),
                    password = password
                )
            } else {
                session = userRepository.add(
                    Customer(
                        id = 0,
                        name = name,
                        phoneNumber = phoneNumber,
                        email = email
                    ),
                    password = password
                )
            }
            sessionCache.saveSession(session)
            return SignUpResult.Success(session.user)
        } catch (e: Exception) {
            return SignUpResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}