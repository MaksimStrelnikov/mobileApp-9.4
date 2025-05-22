package dev.tp_94.mobileapp.basket.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CakeGeneralEntity::class], version = 1, exportSchema = false)
@TypeConverters(CakeGeneralConverter::class)
abstract class BasketDatabase : RoomDatabase() {
    abstract fun basketDao(): BasketDao
}