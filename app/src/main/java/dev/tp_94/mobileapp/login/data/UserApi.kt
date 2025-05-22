package dev.tp_94.mobileapp.login.data

import dev.tp_94.mobileapp.login.data.dto.UserLoginDTO
import dev.tp_94.mobileapp.login.data.dto.UserResponseDTO
import dev.tp_94.mobileapp.signup.data.ConfectionerRegisterDTO
import dev.tp_94.mobileapp.signup.data.CustomerRegisterDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("auth/login/")
    suspend fun login(@Body loginBody: UserLoginDTO): Response<UserResponseDTO>

    @POST("auth/register/")
    suspend fun registerConfectioner(@Body confectionerRegisterDTO: ConfectionerRegisterDTO): Response<UserResponseDTO>

    @POST("auth/register/")
    suspend fun registerCustomer(@Body customerRegisterDTO: CustomerRegisterDTO): Response<UserResponseDTO>
}