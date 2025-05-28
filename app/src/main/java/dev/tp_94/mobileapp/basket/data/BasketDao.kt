package dev.tp_94.mobileapp.basket.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.tp_94.mobileapp.core.models.CakeGeneral

@Dao
interface BasketDao {
    @Insert
    suspend fun insertCakeGeneral(cakeGeneralEntity: CakeGeneralEntity)

    @Query("SELECT * FROM basket WHERE userPhone = :userPhone")
    suspend fun getBasket(userPhone: String): List<CakeGeneralEntity>

    @Query("DELETE FROM basket WHERE userPhone = :userPhone")
    suspend fun clearBasket(userPhone: String)

    @Query("SELECT * FROM basket WHERE userPhone = :userPhone AND cake = :cake LIMIT 1")
    suspend fun findOneBasketEntry(userPhone: String, cake: CakeGeneral): CakeGeneralEntity?

    @Delete
    suspend fun deleteOneBasketEntry(entry: CakeGeneralEntity)

}
