package dev.tp_94.mobileapp.core.models

import android.net.Uri
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

sealed interface Cake {
    val diameter: Float
    val description: String
}

data class CakeCustom(
    var color: Color,
    override val diameter: Float,
    val text: String = "",
    val textOffset: Offset = Offset.Zero,
    val imageUri: Uri? = null,
    val imageOffset: Offset = Offset.Zero,
    val fillings: List<String> = arrayListOf(),
    val days: Int = 3,
    override val description: String = ""
) : Cake

@Serializable
data class SerializableColor(val value: Long)
@Serializable
data class SerializableOffset(val x: Float, val y: Float)
@Serializable
data class SerializableUri(val uri: String?)

fun Color.toSerializable() = SerializableColor(this.value.toLong())
fun SerializableColor.toColor() = Color(this.value)

fun Offset.toSerializable() = SerializableOffset(x, y)
fun SerializableOffset.toOffset() = Offset(x, y)

fun Uri?.toSerializable() = SerializableUri(this?.toString())
fun SerializableUri.toUri() = this.uri?.let { Uri.parse(it) }

@Serializable
data class CakeCustomSerializable(
    val color: SerializableColor,
    val diameter: Float,
    val text: String = "",
    val textOffset: SerializableOffset = SerializableOffset(0f, 0f),
    val imageUri: SerializableUri? = null,
    val imageOffset: SerializableOffset = SerializableOffset(0f, 0f),
    val fillings: List<String> = arrayListOf(),
    val days: Int = 3,
    val description: String = ""
)

fun CakeCustomSerializable.toOriginal(): CakeCustom {
    return CakeCustom(
        color = this.color.toColor(),
        diameter = this.diameter,
        text = this.text,
        textOffset = this.textOffset.toOffset(),
        imageUri = this.imageUri?.toUri(),
        imageOffset = this.imageOffset.toOffset(),
        fillings = this.fillings,
        days = this.days,
        description = this.description
    )
}

fun CakeCustom.toSerializable(): CakeCustomSerializable {
    return CakeCustomSerializable(
        color = this.color.toSerializable(),
        diameter = this.diameter,
        text = this.text,
        textOffset = this.textOffset.toSerializable(),
        imageUri = this.imageUri.toSerializable(),
        imageOffset = this.imageOffset.toSerializable(),
        fillings = this.fillings,
        days = this.days,
        description = this.description
    )
}


@Serializable
data class CakeGeneral(
    val price: Int,
    val name: String,
    override val description: String,
    override val diameter: Float,
    val weight: Float,
    val preparationDays: Int,
    val confectioner: Confectioner
) : Cake
