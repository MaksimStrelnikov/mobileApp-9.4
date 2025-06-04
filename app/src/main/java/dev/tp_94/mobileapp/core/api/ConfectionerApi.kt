package dev.tp_94.mobileapp.core.api

import dev.tp_94.mobileapp.cakes_feed.data.NameBodyDTO
import dev.tp_94.mobileapp.orders.data.dto.ConfectionerResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ConfectionerApi {
    @GET("confectioners")
    suspend fun getConfectioners(): Response<List<ConfectionerResponseDTO>>

    @POST("confectioners/search/name")
    suspend fun getConfectionersByName(@Body nameBodyDTO: NameBodyDTO): Response<List<ConfectionerResponseDTO>>

    @POST("confectioners/sorted/cakes")
    suspend fun getConfectionersByName(@Query("ascending") ascending: Boolean, @Body nameBodyDTO: NameBodyDTO): Response<List<ConfectionerResponseDTO>>
}