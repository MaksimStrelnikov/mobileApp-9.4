package dev.tp_94.mobileapp.login.domain

import dev.tp_94.mobileapp.login.data.dto.UserLoginDTO
import dev.tp_94.mobileapp.login.data.dto.UserResponseDTO
import dev.tp_94.mobileapp.login.data.dto.UserResponseWithTokensDTO
import dev.tp_94.mobileapp.profile_editor.data.dto.ConfectionerUpdateDTO
import dev.tp_94.mobileapp.profile_editor.data.dto.CustomerUpdateDTO
import dev.tp_94.mobileapp.signup.data.ConfectionerRegisterDTO
import dev.tp_94.mobileapp.signup.data.CustomerRegisterDTO

interface UserRepository {
    suspend fun login(loginDTO: UserLoginDTO): UserResponseWithTokensDTO
    suspend fun add(confectioner: ConfectionerRegisterDTO): UserResponseWithTokensDTO
    suspend fun add(customer: CustomerRegisterDTO): UserResponseWithTokensDTO
    suspend fun update(customer: CustomerUpdateDTO): UserResponseDTO
    suspend fun update(confectioner: ConfectionerUpdateDTO): UserResponseDTO
    suspend fun delete()
}