package dev.tp_94.mobileapp.basket.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BasketDao {
    @Insert
    suspend fun insertCakeGeneral(cakeGeneralEntity: CakeGeneralEntity)

    @Query("SELECT * FROM basket WHERE userPhone = :userPhone")
    suspend fun getBasket(userPhone: String): List<CakeGeneralEntity>

    @Query("DELETE FROM basket WHERE userPhone = :userPhone")
    suspend fun clearBasket(userPhone: String)
}
