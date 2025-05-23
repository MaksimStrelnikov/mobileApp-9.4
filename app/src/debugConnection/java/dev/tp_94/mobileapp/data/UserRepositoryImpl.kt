package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.login.data.UserApi
import dev.tp_94.mobileapp.login.data.dto.UserLoginDTO
import dev.tp_94.mobileapp.core.data.HttpStatus.*
import dev.tp_94.mobileapp.core.models.toDto
import dev.tp_94.mobileapp.login.data.dto.UserResponseDTO
import dev.tp_94.mobileapp.login.domain.UserRepository
import dev.tp_94.mobileapp.signup.data.ConfectionerRegisterDTO
import dev.tp_94.mobileapp.signup.data.CustomerRegisterDTO
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserApi
) : UserRepository {
    override suspend fun login(loginDTO: UserLoginDTO): UserResponseDTO {
        val response = api.login(loginDTO)
        if (response.code() == OK.status) {
            return response.body()!!
        } else if (response.code() == UNAUTHORIZED.status || response.code() == FORBIDDEN.status) {
            throw Exception("Неверный логин или пароль")
        }
        throw Exception("Неизвестная ошибка")
    }

    override suspend fun add(confectioner: ConfectionerRegisterDTO): UserResponseDTO {
        val response = api.registerConfectioner(confectioner)
        if (response.code() == CREATED.status) {
            return response.body()!!
        } else if (response.code() == CONFLICT.status) {
            throw Exception("Пользователь с таким номером уже зарегистрирован")
        } else {
            throw Exception("Неизвестная ошибка")
        }
    }

    override suspend fun add(customer: CustomerRegisterDTO): UserResponseDTO {
        val response = api.registerCustomer(customer)
        if (response.code() == CREATED.status) {
            return response.body()!!
        } else if (response.code() == CONFLICT.status) {
            throw Exception("Пользователь с таким номером уже зарегистрирован")
        } else {
            throw Exception("Неизвестная ошибка")
        }
    }

    override suspend fun update(user: User): User {
        TODO("Not yet implemented")
    }

}