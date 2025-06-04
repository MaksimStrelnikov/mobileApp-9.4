package dev.tp_94.mobileapp.core.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Order(
    val id: Long = 0,
    val cake: Cake,
    val date: LocalDate,
    val orderStatus: OrderStatus,
    val price: Int,
    val quantity: Int = 1,
    val customer: Customer,
    val confectioner: Confectioner
)

enum class OrderStatus {
    //backend-fronted statuses
    @SerializedName("pending_approval")
    PENDING_APPROVAL,

    @SerializedName("pending_payment")
    PENDING_PAYMENT,

    @SerializedName("in_progress")
    IN_PROGRESS,

    @SerializedName("done")
    DONE,

    @SerializedName("received")
    RECEIVED,

    @SerializedName("canceled")
    CANCELED,

    @SerializedName("rejected")
    REJECTED
    //frontend-only statuses
}