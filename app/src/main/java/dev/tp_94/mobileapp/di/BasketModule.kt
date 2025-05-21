package dev.tp_94.mobileapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.tp_94.mobileapp.basket.data.BasketDatabase
import dev.tp_94.mobileapp.basket.data.BasketRepositoryImpl
import dev.tp_94.mobileapp.basket.domain.BasketRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BasketModule {

    @Provides
    fun provideBasketRepository(database: BasketDatabase): BasketRepository =
        BasketRepositoryImpl(database)

    @Provides
    @Singleton
    fun provideBasketDatabase(@ApplicationContext context: Context): BasketDatabase =
        Room.databaseBuilder(
            context,
            BasketDatabase::class.java,
            "basket_db"
        ).build()
}