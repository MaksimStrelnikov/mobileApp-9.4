package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.login.domain.UserRepository
import kotlinx.coroutines.delay
import javax.inject.Inject


class MockUserRepository @Inject constructor(private val database: MockDB) : UserRepository {
    override suspend fun login(username: String, password: String): User {
        delay(5000)
        return database.login(username, password)
    }
}