package dev.tp_94.mobileapp.order_payment.presentation

import dev.tp_94.mobileapp.core.models.Card

sealed class NewCardAdditionResult {
    data class Success(val card: Card): NewCardAdditionResult();
    data class Error(val message: String): NewCardAdditionResult();
}