package dev.tp_94.mobileapp.self_made_cake_generator.domain

import dev.tp_94.mobileapp.self_made_cake_generator.presentation.SelfMadeCakeGeneratorResult
import javax.inject.Inject

class GenerateImageUseCase @Inject constructor(
    private val generationRepository: GenerationRepository
) {
    suspend fun execute(
       prompt: String
    ): SelfMadeCakeGeneratorResult {
        try {
            if (prompt.isEmpty()) {
                return SelfMadeCakeGeneratorResult.Error("Пустой запрос")
            }

            val imageUri = //TODO
            return SelfMadeCakeGeneratorResult.Success(imageUri)
        } catch (e: Exception) {
            return SelfMadeCakeGeneratorResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}