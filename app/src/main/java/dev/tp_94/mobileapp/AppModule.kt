package dev.tp_94.mobileapp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tp_94.mobileapp.login.data.MockUserRepository
import dev.tp_94.mobileapp.login.domain.UserRepository

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRepository(): UserRepository {
        return MockUserRepository()
    }
}
