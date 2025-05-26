package dev.tp_94.mobileapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tp_94.mobileapp.core.api.ConfectionerApi
import dev.tp_94.mobileapp.customers_feed.domain.ConfectionerRepository
import dev.tp_94.mobileapp.data.CakeRepositoryImpl
import dev.tp_94.mobileapp.data.ConfectionerRepositoryImpl
import dev.tp_94.mobileapp.data.OrderRepositoryImpl
import dev.tp_94.mobileapp.data.RestrictionsRepositoryImpl
import dev.tp_94.mobileapp.data.UserRepositoryImpl
import dev.tp_94.mobileapp.core.api.AuthApi
import dev.tp_94.mobileapp.login.domain.UserRepository
import dev.tp_94.mobileapp.core.api.UserApi
import dev.tp_94.mobileapp.core.api.CakeApi
import dev.tp_94.mobileapp.core.api.OrderApi
import dev.tp_94.mobileapp.core.api.RestrictionsApi
import dev.tp_94.mobileapp.self_made_cake.domain.CakeRepository
import dev.tp_94.mobileapp.self_made_cake.domain.OrderRepository
import dev.tp_94.mobileapp.self_made_cake.domain.RestrictionsRepository
import dev.tp_94.mobileapp.core.api.GenerationApi
import dev.tp_94.mobileapp.data.WithdrawalRepositoryImpl
import dev.tp_94.mobileapp.self_made_cake_generator.data.GenerationRepositoryImpl
import dev.tp_94.mobileapp.self_made_cake_generator.domain.GenerationRepository
import dev.tp_94.mobileapp.withdrawal.domain.WithdrawalRepository

@Module
@InstallIn(SingletonComponent::class)
object ConnectionsRepositoryModule {

    @Provides
    fun provideUserRepository(authApi: AuthApi, userApi: UserApi): UserRepository {
        return UserRepositoryImpl(authApi, userApi)
    }

    @Provides
    fun provideOrderRepository(orderApi: OrderApi): OrderRepository {
        return OrderRepositoryImpl(orderApi)
    }

    @Provides
    fun provideCakeRepository(cakeApi: CakeApi): CakeRepository {
        return CakeRepositoryImpl(cakeApi)
    }

    @Provides
    fun provideRestrictionsRepository(restrictionsApi: RestrictionsApi): RestrictionsRepository {
        return RestrictionsRepositoryImpl(restrictionsApi)
    }

    @Provides
    fun provideConfectionerRepository(confectionerApi: ConfectionerApi): ConfectionerRepository {
        return ConfectionerRepositoryImpl(confectionerApi)
    }

    @Provides
    fun provideGenerationRepository(generationApi: GenerationApi): GenerationRepository {
        return GenerationRepositoryImpl(generationApi)
    }

    //TODO: await backend
    @Provides
    fun provideWithdrawalRepository(): WithdrawalRepository {
        return WithdrawalRepositoryImpl()
    }
}