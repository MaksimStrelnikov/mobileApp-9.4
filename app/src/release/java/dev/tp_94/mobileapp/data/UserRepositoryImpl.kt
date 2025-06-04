package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.api.AuthApi
import dev.tp_94.mobileapp.login.data.dto.UserLoginDTO
import dev.tp_94.mobileapp.core.data.HttpStatus.*
import dev.tp_94.mobileapp.login.data.dto.UserResponseWithTokensDTO
import dev.tp_94.mobileapp.login.domain.UserRepository
import dev.tp_94.mobileapp.core.api.UserApi
import dev.tp_94.mobileapp.core.api.dto.TokenDTO
import dev.tp_94.mobileapp.login.data.dto.UserResponseDTO
import dev.tp_94.mobileapp.orders.data.dto.ConfectionerResponseDTO
import dev.tp_94.mobileapp.orders.data.dto.CustomerResponseDTO
import dev.tp_94.mobileapp.profile_editor.data.dto.ConfectionerUpdateDTO
import dev.tp_94.mobileapp.profile_editor.data.dto.CustomerUpdateDTO
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
            return response.body()!!
        } else if (response.code() == FORBIDDEN.status || response.code() == UNAUTHORIZED.status) {
            throw Exception("Неверный логин или пароль")
        }
        throw Exception(response.message())
    }

    override suspend fun add(confectioner: ConfectionerRegisterDTO): UserResponseWithTokensDTO {
        val response = authApi.registerConfectioner(confectioner)
        if (response.isSuccessful) {
            return response.body()!!
        } else if (response.code() == CONFLICT.status) {
            throw Exception("Пользователь с таким номером уже зарегистрирован")
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun add(customer: CustomerRegisterDTO): UserResponseWithTokensDTO {
        val response = authApi.registerCustomer(customer)
        if (response.isSuccessful) {
            return response.body()!!
        } else if (response.code() == CONFLICT.status) {
            throw Exception("Пользователь с таким номером уже зарегистрирован")
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun update(customer: CustomerUpdateDTO): CustomerResponseDTO {
        val response = userApi.updateCustomer(customerUpdateDTO = customer)
        if (response.isSuccessful) {
            return response.body()!!.customer!!
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun update(confectioner: ConfectionerUpdateDTO): ConfectionerResponseDTO {
        val response = userApi.updateConfectioner(confectionerUpdateDTO = confectioner)
        if (response.isSuccessful) {
            return response.body()!!.confectioner!!
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun getCurrent(): UserResponseDTO {
        val response = userApi.getCurrentUser()
        if (!response.isSuccessful) throw Exception(response.message())
        return response.body()!!
    }

    override suspend fun delete() {
        val response = userApi.deleteAccount()
        if (!response.isSuccessful) throw Exception(response.message())
    }
}