package dev.tp_94.mobileapp.core.api

import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeResponseDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

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

    //TODO: not yet implemented in backend
    @PUT("cakes/regular/self")
    @Multipart
    suspend fun updateCakeRegular(): Response<CakeResponseDTO>

    @DELETE("cakes/{id}")
    suspend fun deleteCake(@Path("id") id: Long): Response<Unit>

    @GET("cakes")
    suspend fun getAll(): Response<List<CakeResponseDTO>>

    @GET("cakes/confectioner/{confectionerId}")
    suspend fun getAllByConfectioner(@Path("confectionerId") confectionerId: Long): Response<List<CakeResponseDTO>>


}