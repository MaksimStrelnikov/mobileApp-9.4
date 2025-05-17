package dev.tp_94.mobileapp.new_card_addition.presentation

import dev.tp_94.mobileapp.core.models.Card

sealed class NewCardAdditionResult {
    data class Success(val card: Card): NewCardAdditionResult();
    data class Error(val message: String): NewCardAdditionResult();
}