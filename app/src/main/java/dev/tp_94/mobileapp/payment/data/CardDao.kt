package dev.tp_94.mobileapp.payment.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface CardDao {
    @Insert
    suspend fun insertCard(card: CardEntity)

    @Query("SELECT * FROM cards WHERE userPhone = :userPhone")
    suspend fun getCardsByUser(userPhone: String): List<CardEntity>

    @Query("SELECT COUNT(*) FROM cards WHERE userPhone = :userPhone AND number = :number")
    suspend fun countCardByNumber(userPhone: String, number: String): Int
}
