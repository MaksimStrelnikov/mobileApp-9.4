package dev.tp_94.mobileapp.withdrawal.data

import com.google.gson.annotations.SerializedName

data class BalanceResponseDTO(
    @SerializedName("balance_available") val canWithdraw: Int,
    @SerializedName("balance_freezed") val inProgress: Int
)
