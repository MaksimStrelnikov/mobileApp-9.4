package dev.tp_94.mobileapp.payment.data

import com.google.gson.annotations.SerializedName

data class SeveralOrdersCreationPaymentRequestDTO(
    @SerializedName("orders") val orders: List<OrderConciseRequestDTO>,
    @SerializedName("card_number") val cardNumber: String,
    @SerializedName("expiration_date") val expirationDate: String,
    @SerializedName("cvc") val cvc: String,
)
