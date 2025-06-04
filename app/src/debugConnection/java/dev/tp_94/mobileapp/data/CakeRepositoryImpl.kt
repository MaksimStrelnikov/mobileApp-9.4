package dev.tp_94.mobileapp.data

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.tp_94.mobileapp.add_product.CakeGeneralUpdateRequestDTO
import dev.tp_94.mobileapp.cakes_feed.data.NameBodyDTO
import dev.tp_94.mobileapp.cakes_feed.presentation.Sorting
import dev.tp_94.mobileapp.core.api.CakeApi
import dev.tp_94.mobileapp.core.compressImageToMaxSize
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeCustomRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeGeneralRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeResponseDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.toParts
import dev.tp_94.mobileapp.self_made_cake.domain.CakeRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class CakeRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: CakeApi
) : CakeRepository {
    override suspend fun addCustomCake(
        cakeCustomRequestDTO: CakeCustomRequestDTO,
        imageUrl: String?
    ): CakeResponseDTO {
        val response =
            if (imageUrl == null) {
                api.uploadCakeCustomWithoutImage(cakeCustomRequestDTO.toParts())
            } else if (imageUrl.contains("https")) {
                val temp = cakeCustomRequestDTO.copy(imagePath = imageUrl)
                api.uploadCakeCustomWithoutImage(temp.toParts())
            }
            else {
                val uri = Uri.parse(imageUrl)

                val bytes = context.compressImageToMaxSize(uri, maxBytes = 8 * 1024 * 1024)
                val requestBody = bytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
                val part = MultipartBody.Part.createFormData("image", "image.jpg", requestBody)

                api.uploadCakeCustom(cakeCustomRequestDTO.toParts(), part)
            }
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

    override suspend fun getAllByName(nameBodeDTO: NameBodyDTO): List<CakeResponseDTO> {
        val response = api.getAllByName(nameBodeDTO)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun getAllByNameSorted(
        nameBodeDTO: NameBodyDTO,
        sorting: Sorting
    ): List<CakeResponseDTO> {
        val response = when (sorting) {
            Sorting.NO_SORTING -> {
                api.getAllByName(nameBodeDTO)
            }
            Sorting.BY_PRICE_UP -> {
                api.getAllByNameAndPrice(sorting.ascending, nameBodeDTO)
            }
            Sorting.BY_PRICE_DOWN -> {
                api.getAllByNameAndPrice(sorting.ascending, nameBodeDTO)
            }
            Sorting.BY_WEIGHT_UP -> {
                api.getAllByNameAndWeight(sorting.ascending, nameBodeDTO)
            }
            Sorting.BY_WEIGHT_DOWN -> {
                api.getAllByNameAndWeight(sorting.ascending, nameBodeDTO)
            }
        }
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }


    override suspend fun getAllGeneral(): List<CakeResponseDTO> {
        val response = api.getAll()
        if (response.isSuccessful) {
            return response.body()!!.filter { !it.isCustom }
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
        id: Long,
        cakeGeneralRequestDTO: CakeGeneralRequestDTO,
        imageUrl: String
    ) {
        if (!imageUrl.contains("https")) {
            val uri = Uri.parse(imageUrl)

            val bytes = context.compressImageToMaxSize(uri, maxBytes = 8 * 1024 * 1024)
            val requestBody = bytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("image", "image.jpg", requestBody)

            val response = api.updateCakeRegularImage(id, part)
            if (!response.isSuccessful) throw Exception(response.message())
        }
        val response = api.updateCakeRegularWithoutImage(id, cakeGeneralRequestDTO)
        if (!response.isSuccessful) throw Exception(response.message())
    }
}