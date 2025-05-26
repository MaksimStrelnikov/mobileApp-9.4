package dev.tp_94.mobileapp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StableHordeApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainApi

