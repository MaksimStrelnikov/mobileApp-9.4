package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.login.data.UserApi
import dev.tp_94.mobileapp.login.data.dto.UserLoginDTO
import dev.tp_94.mobileapp.core.data.HttpStatus.*
import dev.tp_94.mobileapp.login.domain.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserApi
) : UserRepository {
    override suspend fun login(username: String, password: String): Session {
        val response = api.login(
            UserLoginDTO(
                phone = username,
                password = password
            )
        )
        if (response.code() == OK.status) {
            val user = response.body()!!
            return Session(
                user = if (user.type == "confectioner") {

                        Confectioner(
                            id = user.id,
                            name = user.name,
                            phoneNumber = user.phone,
                            email = user.email,
                            description = user.description ?: "",
                            address = user.address ?: ""
                        )
                } else {
                    Customer(
                        id = user.id,
                        name = user.name,
                        phoneNumber = user.phone,
                        email = user.email
                    )
                },
                //TODO: tokenization
                token = "token"
            )
        } else if (response.code() == UNAUTHORIZED.status || response.code() == FORBIDDEN.status) {
            throw Exception("Неверный логин или пароль")
        }
        throw Exception("Неизвестная ошибка")
    }

    override suspend fun add(confectioner: Confectioner, password: String): Session {
        TODO("Not yet implemented")
    }

    override suspend fun add(customer: Customer, password: String): Session {
        TODO("Not yet implemented")
    }

    override suspend fun update(user: User): User {
        TODO("Not yet implemented")
    }

}