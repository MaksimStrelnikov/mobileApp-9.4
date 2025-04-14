package dev.tp_94.mobileapp.core

import androidx.compose.ui.graphics.Color

fun Color.darken(factor: Float = 0.75f): Color {
    return Color(
        (red * factor * 255).toInt(),
        (green * factor * 255).toInt(),
        (blue * factor * 255).toInt()
    )
}