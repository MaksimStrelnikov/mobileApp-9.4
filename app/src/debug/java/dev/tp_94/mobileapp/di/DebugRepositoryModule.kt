package dev.tp_94.mobileapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.customersfeed.domain.ConfectionerRepository
import dev.tp_94.mobileapp.data.MockConfectionerRepository
import dev.tp_94.mobileapp.data.MockDB
import dev.tp_94.mobileapp.data.MockOrderRepository
import dev.tp_94.mobileapp.data.MockUserRepository
import dev.tp_94.mobileapp.login.domain.UserRepository
import dev.tp_94.mobileapp.selfmadecake.domain.OrderRepository

@Module
@InstallIn(SingletonComponent::class)
object DebugRepositoryModule {

    @Provides
    fun provideUserRepository(sessionCache: SessionCache, db: MockDB): UserRepository {
        return MockUserRepository(sessionCache, db)
    }

    @Provides
    fun provideConfectionerRepository(db: MockDB): ConfectionerRepository {
        return MockConfectionerRepository(db)
    }

    @Provides
    fun provideOrderRepository(db: MockDB): OrderRepository {
        return MockOrderRepository(db)
    }
}
