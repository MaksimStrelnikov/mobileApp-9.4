package dev.tp_94.mobileapp.self_made_cake.presentation

sealed class SelfMadeCakeResult {
    data object Success: SelfMadeCakeResult()
    data class Error(val message: String): SelfMadeCakeResult()
}