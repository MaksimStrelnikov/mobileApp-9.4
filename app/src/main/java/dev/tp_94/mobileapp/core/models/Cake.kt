package dev.tp_94.mobileapp.core.models

import android.net.Uri
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
}
@Serializable(with=CakeCustomSerializer::class)
@SerialName("custom")
data class CakeCustom(
    var color: Color,
    override val diameter: Float,
    val text: String = "",
    val textOffset: Offset = Offset.Zero,
    val imageUri: Uri? = null,
    val imageOffset: Offset = Offset.Zero,
    val fillings: List<String> = arrayListOf(),
    override val preparation: Int = 3,
    override val description: String = "",
    override val name: String = "Индивидуальный торт"
) : Cake

@Serializable
data class SerializableColor(val value: Int)

@Serializable
data class SerializableOffset(val x: Float, val y: Float)

@Serializable
data class SerializableUri(val uri: String?)

fun Color.toSerializable() = SerializableColor(this.toArgb())
fun SerializableColor.toColor() = Color(this.value)

fun Offset.toSerializable() = SerializableOffset(x, y)
fun SerializableOffset.toOffset() = Offset(x, y)

fun Uri?.toSerializable() = SerializableUri(this?.toString())
fun SerializableUri.toUri() = this.uri?.let { Uri.parse(it) }


@Serializable
data class CakeCustomSerializable(
    val name: String,
    val color: SerializableColor,
    val diameter: Float,
    val text: String = "",
    val textOffset: SerializableOffset = SerializableOffset(0f, 0f),
    val imageUri: SerializableUri? = null,
    val imageOffset: SerializableOffset = SerializableOffset(0f, 0f),
    val fillings: List<String> = arrayListOf(),
    val preparation: Int = 3,
    val description: String = ""
)

fun CakeCustomSerializable.toOriginal(): CakeCustom {
    return CakeCustom(
        name = this.name,
        color = this.color.toColor(),
        diameter = this.diameter,
        text = this.text,
        textOffset = this.textOffset.toOffset(),
        imageUri = this.imageUri?.toUri(),
        imageOffset = this.imageOffset.toOffset(),
        fillings = this.fillings,
        preparation = this.preparation,
        description = this.description
    )
}

fun CakeCustom.toSerializable(): CakeCustomSerializable {
    return CakeCustomSerializable(
        name = this.name,
        color = this.color.toSerializable(),
        diameter = this.diameter,
        text = this.text,
        textOffset = this.textOffset.toSerializable(),
        imageUri = this.imageUri.toSerializable(),
        imageOffset = this.imageOffset.toSerializable(),
        fillings = this.fillings,
        preparation = this.preparation,
        description = this.description
    )
}


@Serializable
@SerialName("general")
data class CakeGeneral(
    val price: Int,
    override val name: String,
    override val description: String,
    override val diameter: Float,
    val weight: Float,
    override val preparation: Int,
    val confectioner: Confectioner
) : Cake
