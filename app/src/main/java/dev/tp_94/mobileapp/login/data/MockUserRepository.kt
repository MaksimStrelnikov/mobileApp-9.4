package dev.tp_94.mobileapp.login.data

import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.login.domain.UserRepository
import kotlinx.coroutines.delay


class MockUserRepository() : UserRepository {
    override suspend fun login(username: String, password: String): User {
        delay(5000)
        if (username == "8005553535" && password == "123456789") {
            return Customer(
                id = 1,
                name = "Maksim",
                phoneNumber = username,
                email = "strelochka@vsu.com"
            )
        }
        throw Exception("Неверный номер телефона или пароль")
    }
}