package dev.tp_94.mobileapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.tp_94.mobileapp.order_payment.data.CardDao
import dev.tp_94.mobileapp.order_payment.data.CardDatabase
import dev.tp_94.mobileapp.order_payment.data.CardRepositoryImpl
import dev.tp_94.mobileapp.order_payment.domain.CardRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CardModule {

    @Provides
    fun provideCardRepository(dao: CardDao): CardRepository =
        CardRepositoryImpl(dao)

    @Provides
    fun provideCardDao(db: CardDatabase): CardDao =
        db.cardDao()

    @Provides
    @Singleton
    fun provideCardDatabase(@ApplicationContext context: Context): CardDatabase =
        Room.databaseBuilder(
            context,
            CardDatabase::class.java,
            "card_db"
        ).build()
}
