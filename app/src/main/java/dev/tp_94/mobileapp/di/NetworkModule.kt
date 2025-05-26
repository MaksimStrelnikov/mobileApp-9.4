package dev.tp_94.mobileapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.data.AuthInterceptor
import dev.tp_94.mobileapp.core.data.RetrofitInstance
import dev.tp_94.mobileapp.core.data.TokenAuthenticator
import dev.tp_94.mobileapp.core.api.AuthApi
import dev.tp_94.mobileapp.core.api.CakeApi
import dev.tp_94.mobileapp.core.api.OrderApi
import dev.tp_94.mobileapp.core.api.RestrictionsApi
import dev.tp_94.mobileapp.core.api.GenerationApi
import kotlinx.datetime.Instant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun provideAuthInterceptor(sessionCache: SessionCache): AuthInterceptor =
        AuthInterceptor(sessionCache)

    @Provides
    @MainApi
    fun provideMainOkHttpClient(
        authInterceptor: AuthInterceptor,
        logging: HttpLoggingInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(authInterceptor)
        .authenticator(tokenAuthenticator)
        .build()

    @Provides
    @StableHordeApi
    fun provideHordeOkHttpClient(
        logging: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        // TODO: add apikey insertion through interceptor
        .build()

    @Provides
    fun provideGson(): Gson =
        GsonBuilder()
            .registerTypeAdapter(Instant::class.java, InstantTypeAdapter())
            .create()


    @Provides
    @Singleton
    @MainApi
    fun provideMainRetrofit(@MainApi client: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(RetrofitInstance.MAIN_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    @StableHordeApi
    fun provideHordeRetrofit(client: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(RetrofitInstance.MAIN_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideUserApi(@MainApi retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideOrderApi(@MainApi retrofit: Retrofit): OrderApi =
        retrofit.create(OrderApi::class.java)

    @Provides
    @Singleton
    fun provideRestrictionsApi(@MainApi retrofit: Retrofit): RestrictionsApi =
        retrofit.create(RestrictionsApi::class.java)

    @Provides
    @Singleton
    fun provideCakeApi(@MainApi retrofit: Retrofit): CakeApi =
        retrofit.create(CakeApi::class.java)

    @Provides
    @StableHordeApi
    @Singleton
    fun provideStableHordeRetrofit(@StableHordeApi client: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(RetrofitInstance.HORDE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideGenerationApi(@StableHordeApi retrofit: Retrofit): GenerationApi =
        retrofit.create(GenerationApi::class.java)
}


class InstantTypeAdapter : JsonDeserializer<Instant>, JsonSerializer<Instant> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Instant {
        return Instant.parse(json.asString)
    }

    override fun serialize(
        src: Instant,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}

