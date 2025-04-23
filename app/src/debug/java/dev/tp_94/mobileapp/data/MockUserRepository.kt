package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.core.models.UserPassword
import dev.tp_94.mobileapp.login.domain.UserRepository
import kotlinx.coroutines.delay
import javax.inject.Inject


class MockUserRepository @Inject constructor(
    private val sessionCache: SessionCache,
    private val database: MockDB
) : UserRepository {
    override suspend fun login(username: String, password: String): User {
        delay(1000)
        sessionCache.saveSession(
            Session(
                user = database.login(username, password),
                token = "235453"
            )
        )
        return sessionCache.getActiveSession()!!.user
    }

    override suspend fun add(user: UserPassword): User {
        delay(1000)
        sessionCache.saveSession(
            Session(
                user = database.add(user),
                token = "235453"
            )
        )
        return sessionCache.getActiveSession()!!.user
    }

    override suspend fun update(user: User): User {
        delay(1000)
        sessionCache.saveSession(
            Session(
                user = database.update(user),
                token = "235453"
            )
        )
        return sessionCache.getActiveSession()!!.user
    }
}