package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.User

class MockDB {
    val database: ArrayList<UserPassword> = ArrayList()

    init {
        database.add(
            CustomerPassword(
            id = 1,
            name = "Елена Жужпожуж",
            phoneNumber = "8005553535",
            password = "123456789",
            email = "lenochka.devochka@gigamail.com"
        )
        )
    }

    fun login(username: String, password: String): User {
        for (temp in database) {
            if (temp.phoneNumber == username) {
                if (temp.password == password) {
                    if (temp is CustomerPassword) {
                        return Customer(
                            temp.id,
                            temp.name,
                            temp.phoneNumber,
                            temp.email
                        )
                    }
                    else if (temp is ConfectionerPassword){
                        return Confectioner(
                            temp.id,
                            temp.name,
                            temp.phoneNumber,
                            temp.email,
                            temp.description,
                            temp.address
                        )
                    }
                }
                throw Exception("Неверный пароль")
            }
        }
        throw Exception("Пользователя не существует")
    }
}

data class CustomerPassword(
    override val id: Int,
    override val name: String,
    override val phoneNumber: String,
    override val email: String,
    override val password: String
) : UserPassword()

data class ConfectionerPassword(
    override val id: Int,
    override val name: String,
    override val phoneNumber: String,
    override val email: String,
    val description: String,
    val address: String,
    override val password: String
) : UserPassword()

sealed class UserPassword {
    abstract val id: Int
    abstract val password: String
    abstract val name: String
    abstract val phoneNumber: String
    abstract val email: String
}