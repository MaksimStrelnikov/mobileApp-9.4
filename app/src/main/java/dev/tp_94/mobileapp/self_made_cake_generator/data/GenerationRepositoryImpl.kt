package dev.tp_94.mobileapp.self_made_cake_generator.data

import dev.tp_94.mobileapp.core.api.GenerationApi
import dev.tp_94.mobileapp.self_made_cake_generator.domain.GenerationRepository
import javax.inject.Inject

class GenerationRepositoryImpl @Inject constructor(private val api: GenerationApi): GenerationRepository {
    override suspend fun generateCake(): String? {
        throw Exception("В данный момент генерация изображений недоступна, попробуйте позже")
        /*TODO: implement image generation
        val response = api.generateCake()
        if (response.isSuccessful) {

        } else {
            throw Exception(response.message())
        }*/
    }
}