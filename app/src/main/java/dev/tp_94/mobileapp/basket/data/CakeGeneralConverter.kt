package dev.tp_94.mobileapp.basket.data

import androidx.room.TypeConverter
import dev.tp_94.mobileapp.core.models.CakeGeneral
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CakeGeneralConverter {
    @TypeConverter
    fun fromCake(cake: CakeGeneral): String {
        return Json.encodeToString(cake)
    }

    @TypeConverter
    fun toCake(data: String): CakeGeneral {
        return Json.decodeFromString(data)
    }
}
