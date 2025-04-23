package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.ConfectionerPassword
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.CustomerPassword
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.core.models.UserPassword

class MockDB {
    val database: ArrayList<UserPassword> = ArrayList()
    var current_index = 2

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

    fun add(user: UserPassword): User {
        if (database.filter { it.phoneNumber == user.phoneNumber }.isNotEmpty()) throw Exception("Пользователь с таким номером телефона уже зарегистрирован")
        val temp: UserPassword
        if (user is CustomerPassword) {
            temp = user.copy(id = current_index++)
            database.add(temp)
            return Customer(
                id = temp.id,
                name = temp.name,
                phoneNumber = temp.phoneNumber,
                email = temp.email
            )
        } else if (user is ConfectionerPassword){
            temp = user.copy(id = current_index++)
            database.add(temp)
            return Confectioner(
                id = temp.id,
                name = temp.name,
                phoneNumber = temp.phoneNumber,
                email = temp.email,
                description = temp.description,
                address = temp.address
            )
        }
        throw Exception("Непредвиденная ошибка при добавлении пользователя")
    }

    fun update(user: User): User {
        val filtered = database.filter { it.id == user.id }
        if (filtered.isEmpty()) throw Exception("Пользователя не существует")
        val deleted = filtered.last()
        database.remove(filtered.last())
        if (deleted is CustomerPassword) {
            if (user is Customer) {
                database.add(
                    CustomerPassword(
                        id = deleted.id,
                        name = user.name,
                        phoneNumber = user.phoneNumber,
                        email = user.email,
                        password = deleted.password
                    )
                )
            } else {
                throw AssertionError("User type does not match with one in database")
            }
        } else if (deleted is ConfectionerPassword){
            if (user is Confectioner) {
                database.add(
                    ConfectionerPassword(
                        id = deleted.id,
                        name = user.name,
                        phoneNumber = user.phoneNumber,
                        email = user.email,
                        description = user.description,
                        address = user.address,
                        password = deleted.password
                    )
                )
            } else {
                throw AssertionError("User type does not match with one in database")
            }
        }
        return user
    }
}