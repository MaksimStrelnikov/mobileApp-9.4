package dev.tp_94.mobileapp.payment.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userPhone: String,
    val number: String,
    val expiration: String,
    val cvc: String
)

