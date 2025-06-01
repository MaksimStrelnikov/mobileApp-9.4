package dev.tp_94.mobileapp.withdrawal.presentation

sealed class WithdrawalResult {
    data class Success(val canWithdraw: Int, val inProgress: Int) : WithdrawalResult()
    data class Error(val message: String) : WithdrawalResult()
}