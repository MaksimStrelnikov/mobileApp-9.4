package dev.tp_94.mobileapp.self_made_cake.presentation

import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner

data class SelfMadeCakeState(
    var colorPickerOpen: Boolean = false,
    var textImageEditor: Editor = Editor.IMAGE,
    val cakeCustom: CakeCustom,
    val confectioner: Confectioner,
    val isLoading: Boolean = false,
    val newFilling: String = "",
    val fillings: List<String> = emptyList()
)
