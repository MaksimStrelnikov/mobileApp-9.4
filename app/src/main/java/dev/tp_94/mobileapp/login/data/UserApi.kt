package dev.tp_94.mobileapp.login.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @GET("users/phone/{phone}")
    suspend fun getUserByPhone(
        @Path("phone") phoneNumber: String
    ): Response<UserResponseDTO>

    @POST("users/")
    suspend fun postUser(@Body user: UserRegisterDTO): Response<UserResponseDTO>

    @POST("confectioners/")
    suspend fun postConfectioner(@Body confectionerRegisterDTO: ConfectionerRegisterDTO): ConfectionerResponseDTO

    @POST("customers/")
    suspend fun postCustomer(@Body customerRegisterDTO: CustomerRegisterDTO): CustomerResponseDTO

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") userId: Long, @Body userResponseDTO: UserResponseDTO): Response<UserResponseDTO>

    @PUT("confectioners/{id}")
    suspend fun updateConfectioner(@Path("id") userId: Long, @Body confectionerResponseDTO: ConfectionerResponseDTO): Response<ConfectionerResponseDTO>

    @PUT("customers/{id}")
    suspend fun updateCustomer(@Path("id") userId: Long, @Body customerResponseDTO: CustomerResponseDTO): Response<CustomerResponseDTO>
}