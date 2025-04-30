package dev.tp_94.mobileapp.customersfeed.data

import dev.tp_94.mobileapp.core.models.Confectioner

interface ConfectionerRepository {
    fun getAllByName(name: String): List<Confectioner>
}