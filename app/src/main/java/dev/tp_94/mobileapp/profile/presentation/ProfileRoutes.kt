package dev.tp_94.mobileapp.profile.presentation

data class ProfileConfectionerRoutes(
    val onChangePersonalData: () -> Unit,
    val onWithdraw: () -> Unit,
    val onViewOrders: () -> Unit,
    val onChangeCustomCake: () -> Unit,
)

data class ProfileCustomerRoutes(
    val onChangePersonalData: () -> Unit,
    val onViewOrders: () -> Unit,
)