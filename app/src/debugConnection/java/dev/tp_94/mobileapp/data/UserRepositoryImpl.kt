package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.api.AuthApi
import dev.tp_94.mobileapp.login.data.dto.UserLoginDTO
import dev.tp_94.mobileapp.core.data.HttpStatus.*
import dev.tp_94.mobileapp.login.data.dto.UserResponseDTO
import dev.tp_94.mobileapp.login.data.dto.UserResponseWithTokensDTO
import dev.tp_94.mobileapp.login.domain.UserRepository
import dev.tp_94.mobileapp.core.api.UserApi
import dev.tp_94.mobileapp.profile_editor.data.dto.ConfectionerUpdateDTO
import dev.tp_94.mobileapp.profile_editor.data.dto.CustomerUpdateDTO
import dev.tp_94.mobileapp.profile_editor.data.dto.UserUpdateDTO
import dev.tp_94.mobileapp.signup.data.ConfectionerRegisterDTO
import dev.tp_94.mobileapp.signup.data.CustomerRegisterDTO
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val userApi: UserApi
) : UserRepository {
    override suspend fun login(loginDTO: UserLoginDTO): UserResponseWithTokensDTO {
        val response = authApi.login(loginDTO)
        if (response.isSuccessful) {
            val cookies = response.headers().values("Set-Cookie")

            val accessToken = cookies.find { it.startsWith("accessToken=") }
                ?.substringAfter("accessToken=")
                ?.substringBefore(";")
                ?: throw Exception("Ошибка на стороне сервера! Валидация не прошла успешно")

            val refreshToken = cookies.find { it.startsWith("refreshToken=") }
                ?.substringAfter("refreshToken=")
                ?.substringBefore(";")
                ?: throw Exception("Ошибка на стороне сервера! Валидация не прошла успешно")

            return response.body()!!.toUserResponseWithTokensDTO(accessToken, refreshToken)
        } else if (response.code() == FORBIDDEN.status || response.code() == UNAUTHORIZED.status) {
            throw Exception("Неверный логин или пароль")
        }
        throw Exception(response.message())
    }

    override suspend fun add(confectioner: ConfectionerRegisterDTO): UserResponseWithTokensDTO {
        val response = authApi.registerConfectioner(confectioner)
        if (response.isSuccessful) {
            val cookies = response.headers().values("Set-Cookie")

            val accessToken = cookies.find { it.startsWith("accessToken=") }
                ?.substringAfter("accessToken=")
                ?.substringBefore(";")
                ?: throw Exception("Ошибка на стороне сервера! Валидация не прошла успешно")

            val refreshToken = cookies.find { it.startsWith("refreshToken=") }
                ?.substringAfter("refreshToken=")
                ?.substringBefore(";")
                ?: throw Exception("Ошибка на стороне сервера! Валидация не прошла успешно")

            return response.body()!!.toUserResponseWithTokensDTO(accessToken, refreshToken)
        } else if (response.code() == CONFLICT.status) {
            throw Exception("Пользователь с таким номером уже зарегистрирован")
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun add(customer: CustomerRegisterDTO): UserResponseWithTokensDTO {
        val response = authApi.registerCustomer(customer)
        if (response.isSuccessful) {
            val cookies = response.headers().values("Set-Cookie")

            val accessToken = cookies.find { it.startsWith("accessToken=") }
                ?.substringAfter("accessToken=")
                ?.substringBefore(";")
                ?: throw Exception("Ошибка на стороне сервера! Валидация не прошла успешно")

            val refreshToken = cookies.find { it.startsWith("refreshToken=") }
                ?.substringAfter("refreshToken=")
                ?.substringBefore(";")
                ?: throw Exception("Ошибка на стороне сервера! Валидация не прошла успешно")

            return response.body()!!.toUserResponseWithTokensDTO(accessToken, refreshToken)
        } else if (response.code() == CONFLICT.status) {
            throw Exception("Пользователь с таким номером уже зарегистрирован")
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun update(customer: CustomerUpdateDTO): UserResponseDTO {
        val response = userApi.updateCustomer(customerUpdateDTO = customer)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun update(confectioner: ConfectionerUpdateDTO): UserResponseDTO {
        val response = userApi.updateConfectioner(UserUpdateDTO(confectionerUpdateDTO = confectioner))
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun delete() {
        val response = userApi.deleteAccount()
        if (!response.isSuccessful) throw Exception(response.message())
    }
}