package dev.tp_94.mobileapp.core.api

import dev.tp_94.mobileapp.login.data.dto.UserResponseDTO
import dev.tp_94.mobileapp.profile_editor.data.dto.ConfectionerUpdateDTO
import dev.tp_94.mobileapp.profile_editor.data.dto.CustomerUpdateDTO
import dev.tp_94.mobileapp.profile_editor.data.dto.UserUpdateDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface UserApi {
    @PATCH("users/self")
    suspend fun updateCustomer(@Body customerUpdateDTO: CustomerUpdateDTO): Response<UserResponseDTO>

    @PATCH("users/self")
    suspend fun updateConfectioner(@Body confectionerUpdateDTO: ConfectionerUpdateDTO): Response<UserResponseDTO>

    @DELETE("users/self")
    suspend fun deleteAccount(): Response<Unit>

    @GET("users/current")
    suspend fun getCurrentUser(): Response<UserResponseDTO>
}