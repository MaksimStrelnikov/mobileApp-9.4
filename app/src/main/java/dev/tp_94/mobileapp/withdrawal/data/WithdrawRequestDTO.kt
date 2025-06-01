package dev.tp_94.mobileapp.withdrawal.data

import com.google.gson.annotations.SerializedName

data class WithdrawRequestDTO(
    @SerializedName("amount") val amount: Int,
    @SerializedName("card_number") val number: String,
    @SerializedName("expiration_date") val expiration: String,
    @SerializedName("cvc") val cvc: String,
)
