package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.customers_feed.domain.ConfectionerRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class MockConfectionerRepository @Inject constructor(private val db: MockDB): ConfectionerRepository {
    override suspend fun getAllByName(name: String): List<Confectioner> {
        delay(1000)
        return db.getAllConfectionersWithName(name)
    }

    override suspend fun getAll(): List<Confectioner> {
        delay(1000)
        return db.getAllConfectioners()
    }
}