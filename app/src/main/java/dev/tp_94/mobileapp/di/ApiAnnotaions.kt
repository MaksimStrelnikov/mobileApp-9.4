package dev.tp_94.mobileapp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GenerateApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainApiWithoutAuth

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HeadersLog

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BodyLog

