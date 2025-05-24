package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.data.HttpStatus.*
import dev.tp_94.mobileapp.self_made_cake.data.CakeApi
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeCustomRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeResponseDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.toParts
import dev.tp_94.mobileapp.self_made_cake.domain.CakeRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class CakeRepositoryImpl @Inject constructor(private val api: CakeApi): CakeRepository {
    override suspend fun addCustomCake(cakeCustomRequestDTO: CakeCustomRequestDTO, imageUrl: String?): CakeResponseDTO {
        val file = imageUrl?.let { File(it) }
        val requestBody = file?.asRequestBody("image/*".toMediaTypeOrNull())
        val multipartBody = file?.let { requestBody?.let {MultipartBody.Part.createFormData("image", file.name, requestBody) }}

        val response = api.uploadCakeCustom(cakeCustomRequestDTO.toParts(), multipartBody)
        if (response.code() == CREATED.status) {
            return response.body()!!
        } else {
            throw Exception("Неизвестная ошибка")
        }
    }
}