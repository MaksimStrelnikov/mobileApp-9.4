package dev.tp_94.mobileapp.selfmadecake.presentation

import dev.tp_94.mobileapp.core.models.CakeCustom

data class SelfMadeCakeState(
    var colorPickerOpen: Boolean = false,
    var textImageEditor: Editor = Editor.IMAGE,
    val cakeCustom: CakeCustom
)
