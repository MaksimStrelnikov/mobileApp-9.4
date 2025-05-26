package dev.tp_94.mobileapp.self_made_cake_generator.presentation

import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Restrictions

data class SelfMadeCakeGeneratorState (
    val cakeCustom: CakeCustom,
    val confectioner: Confectioner,
    val isLoading: Boolean = false,
    val isGenerating: Boolean = false,
    val prompt: String = "",
    val error: String? = null,
    val restrictions: Restrictions
)