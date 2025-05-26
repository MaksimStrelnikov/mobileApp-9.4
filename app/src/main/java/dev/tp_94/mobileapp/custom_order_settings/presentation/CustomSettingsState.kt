package dev.tp_94.mobileapp.custom_order_settings.presentation

data class CustomSettingsState(
    val isCustomAcceptable: Boolean = false,
    val isImageAcceptable: Boolean = false,
    val isShapeAcceptable: Boolean = false,
    val minDiameter: String = "0",
    val maxDiameter: String = "0",
    val minWorkPeriod: String = "0",
    val maxWorkPeriod: String = "0",
    val newFilling: String = "",
    val fillings: List<String> = emptyList(),
    val error: String? = null
)
