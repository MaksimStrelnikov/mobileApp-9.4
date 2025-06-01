package dev.tp_94.mobileapp.data

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.tp_94.mobileapp.core.api.CakeApi
import dev.tp_94.mobileapp.core.compressImageToMaxSize
import dev.tp_94.mobileapp.core.uriToRequestBody
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeCustomRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeGeneralRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeResponseDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.toParts
import dev.tp_94.mobileapp.self_made_cake.domain.CakeRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class CakeRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context, private val api: CakeApi) : CakeRepository {
    override suspend fun addCustomCake(
        cakeCustomRequestDTO: CakeCustomRequestDTO,
        imageUrl: String?
    ): CakeResponseDTO {
        val uri = Uri.parse(imageUrl)

        val bytes = context.compressImageToMaxSize(uri, maxBytes = 8 * 1024 * 1024)
        val requestBody = bytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("image", "image.jpg", requestBody)

        val response = api.uploadCakeCustom(cakeCustomRequestDTO.toParts(), part)

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun addGeneralCake(
        cakeGeneralRequestDTO: CakeGeneralRequestDTO,
        imageUrl: String
    ): CakeResponseDTO {
        val uri = Uri.parse(imageUrl)

        val bytes = context.compressImageToMaxSize(uri, maxBytes = 8 * 1024 * 1024)
        val requestBody = bytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("image", "image.jpg", requestBody)

        val response = api.uploadCakeRegular(cakeGeneralRequestDTO.toParts(), part)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun deleteCake(id: Long) {
        val response = api.deleteCake(id)
        if (!response.isSuccessful) throw Exception(response.message())
    }

    override suspend fun getAllByName(text: String): List<CakeResponseDTO> {
        return getAll()
        /* TODO: change after it'll be implemented in backend
        val response = api.getAllByName(text)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }*/
    }

    override suspend fun getAll(): List<CakeResponseDTO> {
        val response = api.getAll()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun getAllByConfectioner(confectionerId: Long): List<CakeResponseDTO> {
        val response = api.getAllByConfectioner(confectionerId)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun updateGeneralCake(
        cakeGeneralRequestDTO: CakeGeneralRequestDTO,
        imageUrl: String
    ) {
        TODO("Not yet implemented")
    }
}