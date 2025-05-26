package dev.tp_94.mobileapp.profile_editor.data.dto

import com.google.gson.annotations.SerializedName

data class UserUpdateDTO(
    @SerializedName("customer") val customerUpdateDTO: CustomerUpdateDTO? = null,
    @SerializedName("confectioner") val confectionerUpdateDTO: ConfectionerUpdateDTO? = null
)
