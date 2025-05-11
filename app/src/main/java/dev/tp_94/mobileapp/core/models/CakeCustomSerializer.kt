package dev.tp_94.mobileapp.core.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object CakeCustomSerializer : KSerializer<CakeCustom> {
    override val descriptor: SerialDescriptor =
        CakeCustomSerializable.serializer().descriptor

    override fun serialize(encoder: Encoder, value: CakeCustom) {
        val surrogate = value.toSerializable()
        encoder.encodeSerializableValue(CakeCustomSerializable.serializer(), surrogate)
    }

    override fun deserialize(decoder: Decoder): CakeCustom {
        val surrogate = decoder.decodeSerializableValue(CakeCustomSerializable.serializer())
        return surrogate.toOriginal()
    }
}