package dev.tp_94.mobileapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.data.MockDB
import dev.tp_94.mobileapp.data.MockUserRepository
import dev.tp_94.mobileapp.login.domain.UserRepository

@Module
@InstallIn(SingletonComponent::class)
object DebugRepositoryModule {

    @Provides
    fun provideUserRepository(sessionCache: SessionCache, db: MockDB): UserRepository {
        return MockUserRepository(sessionCache, db)
    }
}
