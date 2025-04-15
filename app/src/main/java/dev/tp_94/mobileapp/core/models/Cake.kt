package dev.tp_94.mobileapp.core.models

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class Cake(
    var color: Color, val diameter: Float, val text: String = "", val textOffset: Offset = Offset.Zero
)
