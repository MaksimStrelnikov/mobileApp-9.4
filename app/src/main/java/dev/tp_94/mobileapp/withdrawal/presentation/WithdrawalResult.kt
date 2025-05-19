package dev.tp_94.mobileapp.withdrawal.presentation

sealed class WithdrawalResult {
    data object Success : WithdrawalResult()
    data class Error(val message: String) : WithdrawalResult()
}