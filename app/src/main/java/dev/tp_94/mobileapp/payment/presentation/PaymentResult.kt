package dev.tp_94.mobileapp.payment.presentation

sealed class PaymentResult {
    data object Success : PaymentResult()
    data object Error: PaymentResult()
}