package dev.tp_94.mobileapp.login.data

import dev.tp_94.mobileapp.login.data.dto.UserLoginDTO
import dev.tp_94.mobileapp.login.data.dto.UserResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("auth/login/")
    suspend fun login(@Body loginBody: UserLoginDTO): Response<UserResponseDTO>
}