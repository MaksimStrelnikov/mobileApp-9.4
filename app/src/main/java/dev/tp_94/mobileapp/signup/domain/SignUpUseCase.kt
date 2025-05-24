package dev.tp_94.mobileapp.signup.domain

import android.util.Patterns
import coil.map.Mapper
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.core.models.toConfectioner
import dev.tp_94.mobileapp.core.models.toCustomer
import dev.tp_94.mobileapp.core.models.toDto
import dev.tp_94.mobileapp.login.data.dto.UserResponseDTO
import dev.tp_94.mobileapp.login.domain.UserRepository
import dev.tp_94.mobileapp.signup.presenatation.SignUpResult
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionCache: SessionCache,
    private val confectionerMapper: Mapper<UserResponseDTO, Confectioner>,
    private val customerMapper: Mapper<UserResponseDTO, Customer>
) {
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
        if (isConfectioner) {
            if (address.isEmpty()) {
                return SignUpResult.Error("Адрес не указан")
            }
        }
        try {
            val user: User
            if (isConfectioner) {
                user = userRepository.add(
                    Confectioner(
                        name = name,
                        phoneNumber = phoneNumber,
                        email = email,
                        description = "",
                        address = address,
                        id = 0,
                        canWithdrawal = 0,
                        inProcess = 0
                    ).toDto(password)
                ).toConfectioner()
            } else {
                user = userRepository.add(
                    Customer(
                        id = 0,
                        name = name,
                        phoneNumber = phoneNumber,
                        email = email
                    ).toDto(password)
                ).toCustomer()
            }
            sessionCache.saveSession(Session(user, "token" /*TODO: tokenization BACKEND AWAITING*/))
            return SignUpResult.Success(user)
        } catch (e: Exception) {
            return SignUpResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}