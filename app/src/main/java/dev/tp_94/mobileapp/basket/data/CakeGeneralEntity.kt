package dev.tp_94.mobileapp.basket.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.tp_94.mobileapp.core.models.CakeGeneral

@Entity(tableName = "basket")
data class CakeGeneralEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userPhone: String,
    val cake: CakeGeneral
)
