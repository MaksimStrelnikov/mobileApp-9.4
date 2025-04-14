package dev.tp_94.mobileapp.selfmadecake.presentation

import dev.tp_94.mobileapp.core.models.Cake

data class ScreenState(
    var colorPickerOpen: Boolean = false,
    val cake: Cake
)
