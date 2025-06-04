package dev.tp_94.mobileapp.self_made_cake_generator.data

import com.google.gson.annotations.SerializedName

data class GenerationRequestDTO(
    @SerializedName("prompt") val prompt: String
)
