package dev.tp_94.mobileapp.self_made_cake.presentation

import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Restrictions

data class SelfMadeCakeState(
    var colorPickerOpen: Boolean = false,
    var textImageEditor: Editor = Editor.TEXT,
    val cakeCustom: CakeCustom,
    val isLoading: Boolean = false,
    val restrictions: Restrictions,
    val error: String? = null
)
