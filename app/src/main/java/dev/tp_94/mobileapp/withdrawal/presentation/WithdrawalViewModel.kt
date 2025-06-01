package dev.tp_94.mobileapp.withdrawal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.payment.domain.GetAllCardsUseCase
import dev.tp_94.mobileapp.payment.presentation.PaymentMethodsResult
import dev.tp_94.mobileapp.withdrawal.domain.GetBalanceUseCase
import dev.tp_94.mobileapp.withdrawal.domain.WithdrawalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val withdrawalUseCase: WithdrawalUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(
        WithdrawalState(
            available = 0,
            inProgress = 0
        )
    )
    val state = _state.asStateFlow()

    fun onSumChange(it: Int) {
        _state.value = _state.value.copy(sum = it)
    }

    fun onSelect(it: Card?) {
        _state.value = _state.value.copy(selected = it)
    }

    fun onWithdraw(
        card: Card,
        sum: Int,
        onSuccessfulWithdrawal: () -> Unit,
        onErrorWithdrawal: () -> Unit
    ) {
        viewModelScope.launch {
            val result = withdrawalUseCase.execute(card, sum, _state.value.available)
            if (result is WithdrawalResult.Success) {
                onSuccessfulWithdrawal()
                update()
            } else {
                onErrorWithdrawal()
                update()
            }
        }
    }

    private fun getBalance() {
        viewModelScope.launch {
            val result = getBalanceUseCase.execute()
            if (result is WithdrawalResult.Success) {
                _state.value = _state.value.copy(
                    available = result.canWithdraw,
                    inProgress = result.inProgress
                )
            } else {
                //TODO: add error handling
            }
        }
    }

    private fun refreshCards() {
        viewModelScope.launch {
            val result = getAllCardsUseCase.execute()
            if (result is PaymentMethodsResult.Success) {
                _state.value = _state.value.copy(cards = result.cards)
            } else {
                //TODO: add error handling
            }
        }
    }

    fun update() {
        getBalance()
        refreshCards()
    }

    init {
        update()
    }
}