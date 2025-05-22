package dev.tp_94.mobileapp.custom_order_settings.presentation

data class CustomSettingsState(
    val isCustomAcceptable: Boolean = false,
    val isImageAcceptable: Boolean = false,
    val isShapeAcceptable: Boolean = false,
    val minDiameter: String = "",
    val maxDiameter: String = "",
    val minWorkPeriod: String = "",
    val maxWorkPeriod: String = "",
    val newFilling: String = "",
    val fillings: List<String> = emptyList()
)
