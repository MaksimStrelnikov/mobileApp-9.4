package dev.tp_94.mobileapp.self_made_cake_generator.data

import com.google.gson.annotations.SerializedName

data class GenerationResponseDTO(
    @SerializedName("image_path") val image: String
)
