package dev.tp_94.mobileapp.withdrawal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.payment.domain.GetAllCardsUseCase
import dev.tp_94.mobileapp.payment.presentation.PaymentMethodsResult
import dev.tp_94.mobileapp.withdrawal.domain.WithdrawalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val sessionCache: SessionCache,
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val withdrawalUseCase: WithdrawalUseCase
) : ViewModel() {
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
            val result = withdrawalUseCase.execute(card, sum)
            if (result is WithdrawalResult.Success) {
                onSuccessfulWithdrawal()
                update()
            } else {
                onErrorWithdrawal()
                update()
            }
        }
    }

    private val _state = MutableStateFlow(
        WithdrawalState(
            available = (sessionCache.session.value!!.user as Confectioner).canWithdrawal,
            inProcess = (sessionCache.session.value!!.user as Confectioner).inProcess
        )
    )
    val state = _state.asStateFlow()

    private suspend fun update() {
        _state.value = _state.value.copy(
            available = (sessionCache.session.value!!.user as Confectioner).canWithdrawal,
            inProcess = (sessionCache.session.value!!.user as Confectioner).inProcess,
            cards = (getAllCardsUseCase.execute() as PaymentMethodsResult.Success).cards
        )
    }
}