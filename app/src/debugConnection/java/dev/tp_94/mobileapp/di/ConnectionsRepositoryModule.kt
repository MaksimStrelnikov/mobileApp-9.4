package dev.tp_94.mobileapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.data.UserRepositoryImpl
import dev.tp_94.mobileapp.login.data.UserApi
import dev.tp_94.mobileapp.login.domain.UserRepository

@Module
@InstallIn(SingletonComponent::class)
object ConnectionsRepositoryModule {

    @Provides
    fun provideUserRepository(sessionCache: SessionCache, userApi: UserApi): UserRepository {
        return UserRepositoryImpl(sessionCache, userApi)
    }
}