package dev.tp_94.mobileapp.core.models

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

object CakeSerializerModule {
    val module = SerializersModule {
        polymorphic(Cake::class) {
            subclass(CakeCustom::class, CakeCustomSerializer)
            subclass(CakeGeneral::class, CakeGeneral.serializer())
        }
    }
}