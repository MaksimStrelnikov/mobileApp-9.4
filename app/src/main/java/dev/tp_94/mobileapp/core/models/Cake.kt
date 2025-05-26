package dev.tp_94.mobileapp.core.models

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Polymorphic
sealed interface Cake {
    val name: String
    val preparation: Int
    val diameter: Float
    val description: String
    val confectioner: Confectioner
}
@Serializable(with=CakeCustomSerializer::class)
@SerialName("custom")
data class CakeCustom(
    var color: Color,
    override val diameter: Float,
    val text: String = "",
    val textOffset: Offset = Offset.Zero,
    val imageUrl: String? = null,
    val imageOffset: Offset = Offset.Zero,
    val fillings: List<String> = arrayListOf(),
    override val preparation: Int = 3,
    override val description: String = "",
    override val name: String = "Индивидуальный торт",
    override val confectioner: Confectioner
) : Cake

@Serializable
data class SerializableColor(val value: Int)

@Serializable
data class SerializableOffset(val x: Float, val y: Float)

fun Color.toSerializable() = SerializableColor(this.toArgb())
fun SerializableColor.toColor() = Color(this.value)

fun Offset.toSerializable() = SerializableOffset(x, y)
fun SerializableOffset.toOffset() = Offset(x, y)



@Serializable
data class CakeCustomSerializable(
    val name: String,
    val color: SerializableColor,
    val diameter: Float,
    val text: String = "",
    val textOffset: SerializableOffset = SerializableOffset(0f, 0f),
    val imageUrl: String? = null,
    val imageOffset: SerializableOffset = SerializableOffset(0f, 0f),
    val fillings: List<String> = arrayListOf(),
    val preparation: Int = 3,
    val description: String = "",
    val confectioner: Confectioner
)

fun CakeCustomSerializable.toOriginal(): CakeCustom {
    return CakeCustom(
        name = this.name,
        color = this.color.toColor(),
        diameter = this.diameter,
        text = this.text,
        textOffset = this.textOffset.toOffset(),
        imageUrl = this.imageUrl,
        imageOffset = this.imageOffset.toOffset(),
        fillings = this.fillings,
        preparation = this.preparation,
        description = this.description,
        confectioner = this.confectioner
    )
}

fun CakeCustom.toSerializable(): CakeCustomSerializable {
    return CakeCustomSerializable(
        name = this.name,
        color = this.color.toSerializable(),
        diameter = this.diameter,
        text = this.text,
        textOffset = this.textOffset.toSerializable(),
        imageUrl = this.imageUrl,
        imageOffset = this.imageOffset.toSerializable(),
        fillings = this.fillings,
        preparation = this.preparation,
        description = this.description,
        confectioner = this.confectioner
    )
}


@Serializable
@SerialName("general")
data class CakeGeneral(
    val id: Long = 0,
    val price: Int = 0,
    val imageUrl: String? = null,
    override val name: String = "",
    override val description: String = "",
    override val diameter: Float = 0f,
    val weight: Float = 0f,
    override val preparation: Int = 0,
    override val confectioner: Confectioner
) : Cake
