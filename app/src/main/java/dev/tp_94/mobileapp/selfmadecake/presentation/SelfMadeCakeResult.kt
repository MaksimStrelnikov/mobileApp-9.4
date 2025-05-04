package dev.tp_94.mobileapp.selfmadecake.presentation

sealed class SelfMadeCakeResult {
    data class Success(val message: String = "Успех"): SelfMadeCakeResult()
    data class Error(val message: String): SelfMadeCakeResult()
}