package dev.tp_94.mobileapp.self_made_cake_generator.presentation

import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner

//TODO: GET RID OF F***ING FILLINGS

data class SelfMadeCakeGeneratorState (
    val cakeCustom: CakeCustom,
    val confectioner: Confectioner,
    val isLoading: Boolean = false,
    val isGenerating: Boolean = false,
    val prompt: String = "",
    val fillings: List<String> = emptyList(),
    val error: String? = null
)