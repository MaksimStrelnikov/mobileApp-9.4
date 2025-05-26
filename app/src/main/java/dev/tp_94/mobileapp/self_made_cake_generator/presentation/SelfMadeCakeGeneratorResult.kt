package dev.tp_94.mobileapp.self_made_cake_generator.presentation

sealed class SelfMadeCakeGeneratorResult {
    data class Success(val imageUrl: String): SelfMadeCakeGeneratorResult()
    data class Error(val message: String): SelfMadeCakeGeneratorResult()
}