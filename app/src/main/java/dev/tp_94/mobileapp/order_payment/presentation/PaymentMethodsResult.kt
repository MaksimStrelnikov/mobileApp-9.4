package dev.tp_94.mobileapp.order_payment.presentation

import dev.tp_94.mobileapp.core.models.Card

sealed class PaymentMethodsResult {
    data class Success(val cards: List<Card>) : PaymentMethodsResult();
    data class Error(val message: String) : PaymentMethodsResult();
}