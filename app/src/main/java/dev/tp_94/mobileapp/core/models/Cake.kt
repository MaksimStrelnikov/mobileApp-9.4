package dev.tp_94.mobileapp.core.models

import android.net.Uri
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

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
    override val description: String = ""
) : Cake

data class CakeGeneral(
    val price: Int,
    val name: String,
    override val description: String,
    override val diameter: Float,
    val weight: Float,
    val preparationDays: Int,
    val confectioner: Confectioner
) : Cake
