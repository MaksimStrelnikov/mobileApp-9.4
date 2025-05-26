package dev.tp_94.mobileapp.core.api

import dev.tp_94.mobileapp.core.data.TokenDTO
import dev.tp_94.mobileapp.login.data.dto.UserLoginDTO
import dev.tp_94.mobileapp.login.data.dto.UserResponseDTO
import dev.tp_94.mobileapp.login.data.dto.UserResponseWithTokensDTO
import dev.tp_94.mobileapp.signup.data.ConfectionerRegisterDTO
import dev.tp_94.mobileapp.signup.data.CustomerRegisterDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login/")
    suspend fun login(@Body loginBody: UserLoginDTO): Response<UserResponseWithTokensDTO>

    @POST("auth/register/")
    suspend fun registerConfectioner(@Body confectionerRegisterDTO: ConfectionerRegisterDTO): Response<UserResponseWithTokensDTO>

    @POST("auth/register/")
    suspend fun registerCustomer(@Body customerRegisterDTO: CustomerRegisterDTO): Response<UserResponseWithTokensDTO>
    //TODO: add revoke at the start of the app
}