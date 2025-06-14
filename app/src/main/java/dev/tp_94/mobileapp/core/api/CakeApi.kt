package dev.tp_94.mobileapp.core.api

import dev.tp_94.mobileapp.cakes_feed.data.NameBodyDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeGeneralRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeResponseDTO
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface CakeApi {
    @POST("cakes/custom")
    @Multipart
    suspend fun uploadCakeCustom(@Part parts: List<MultipartBody.Part>, @Part image: MultipartBody.Part): Response<CakeResponseDTO>

    @POST("cakes/custom")
    @Multipart
    suspend fun uploadCakeCustomWithoutImage(@Part parts: List<MultipartBody.Part>): Response<CakeResponseDTO>

    @POST("cakes/regular/self")
    @Multipart
    suspend fun uploadCakeRegular(@Part parts: List<MultipartBody.Part>, @Part image: MultipartBody.Part): Response<CakeResponseDTO>

    @DELETE("cakes/{id}")
    suspend fun deleteCake(@Path("id") id: Long): Response<Unit>

    @GET("cakes")
    suspend fun getAll(): Response<List<CakeResponseDTO>>

    @POST("cakes/search/name")
    suspend fun getAllByName(@Body name: NameBodyDTO): Response<List<CakeResponseDTO>>

    @POST("cakes/sorted/weight")
    suspend fun getAllByNameAndWeight(@Query("ascending") ascending: Boolean, @Body name: NameBodyDTO): Response<List<CakeResponseDTO>>

    @POST("cakes/sorted/price")
    suspend fun getAllByNameAndPrice(@Query("ascending") ascending: Boolean, @Body name: NameBodyDTO): Response<List<CakeResponseDTO>>

    @GET("cakes/confectioner/{confectionerId}")
    suspend fun getAllByConfectioner(@Path("confectionerId") confectionerId: Long): Response<List<CakeResponseDTO>>

    @PUT("cakes/{id}")
    suspend fun updateCakeRegularWithoutImage(@Path("id") id: Long, @Body cakeGeneralRequestDTO: CakeGeneralRequestDTO): Response<CakeResponseDTO>

    @PATCH("cakes/{id}/image")
    @Multipart
    suspend fun updateCakeRegularImage(@Path("id") id: Long, @Part image: MultipartBody.Part): Response<CakeResponseDTO>


}