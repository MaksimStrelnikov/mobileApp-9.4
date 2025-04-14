package dev.tp_94.mobileapp.core.themes

import androidx.compose.ui.graphics.Color

object Colors{
    val BACKGROUND = Color(255, 251, 239, 255)
}

fun Color.darken(factor: Float = 0.75f): Color {
    return Color(
        (red * factor * 255).toInt(),
        (green * factor * 255).toInt(),
        (blue * factor * 255).toInt()
    )
}