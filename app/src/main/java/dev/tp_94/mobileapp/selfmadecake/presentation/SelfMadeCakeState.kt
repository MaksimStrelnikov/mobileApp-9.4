package dev.tp_94.mobileapp.selfmadecake.presentation

import dev.tp_94.mobileapp.core.models.Cake

data class SelfMadeCakeState(
    var colorPickerOpen: Boolean = false,
    var textImageEditor: Editor = Editor.IMAGE,
    val cake: Cake
)
