package dev.tp_94.mobileapp.payment.data

import com.google.gson.annotations.SerializedName

data class CardDTO(
    @SerializedName("card_number") val number: String,
    @SerializedName("expiration_date") val expiration: String,
    @SerializedName("cvc") val cvc: String
)
