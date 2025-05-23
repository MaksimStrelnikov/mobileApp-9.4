package dev.tp_94.mobileapp.self_made_cake.data

import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeCustomRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeGeneralRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeResponseDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface CakeApi {
    @POST("cakes/custom")
    @Multipart
    suspend fun uploadCakeCustom(@PartMap map: Map<String, RequestBody>, @Part image: MultipartBody.Part?): Response<CakeResponseDTO>

    @POST("cakes/regular")
    @Multipart
    suspend fun uploadCakeRegular(@Body cakeGeneralRequestDTO: CakeGeneralRequestDTO, @Part image: MultipartBody.Part?): Response<CakeResponseDTO>
}