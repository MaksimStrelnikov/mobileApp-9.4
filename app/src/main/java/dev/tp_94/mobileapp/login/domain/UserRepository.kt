package dev.tp_94.mobileapp.login.domain

import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.core.models.UserPassword
import dev.tp_94.mobileapp.login.data.dto.UserLoginDTO
import dev.tp_94.mobileapp.login.data.dto.UserResponseDTO
import dev.tp_94.mobileapp.signup.data.ConfectionerRegisterDTO
import dev.tp_94.mobileapp.signup.data.CustomerRegisterDTO

interface UserRepository {
    suspend fun login(loginDTO: UserLoginDTO): UserResponseDTO
    suspend fun add(confectioner: ConfectionerRegisterDTO): UserResponseDTO
    suspend fun add(customer: CustomerRegisterDTO): UserResponseDTO
    suspend fun update(user: User): User
}