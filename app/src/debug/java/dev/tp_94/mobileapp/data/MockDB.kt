package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.ConfectionerPassword
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.CustomerPassword
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.core.models.UserPassword

class MockDB {
    val usersDatabase: ArrayList<UserPassword> = ArrayList()
    var current_index = 2

    init {
        usersDatabase.add(
            CustomerPassword(
                id = 1,
                name = "Елена Жужпожуж",
                phoneNumber = "8005553535",
                password = "123456789",
                email = "lenochka.devochka@gigamail.com"
            )
        )
        usersDatabase.add(
            ConfectionerPassword(
                id = 2,
                name = "Тортодел",
                phoneNumber = "9876543210",
                password = "123456789",
                email = "cake.make@gigamail.com",
                description = "Просто делаем просто торты",
                address = "г. Воронеж, Университетская площадь, 1"
            )
        )
    }

    fun login(username: String, password: String): User {
        for (temp in usersDatabase) {
            if (temp.phoneNumber == username) {
                if (temp.password == password) {
                    if (temp is CustomerPassword) {
                        return Customer(
                            temp.id,
                            temp.name,
                            temp.phoneNumber,
                            temp.email
                        )
                    } else if (temp is ConfectionerPassword) {
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
        if (usersDatabase.filter { it.phoneNumber == user.phoneNumber }
                .isNotEmpty()) throw Exception("Пользователь с таким номером телефона уже зарегистрирован")
        val temp: UserPassword
        if (user is CustomerPassword) {
            temp = user.copy(id = current_index++)
            usersDatabase.add(temp)
            return Customer(
                id = temp.id,
                name = temp.name,
                phoneNumber = temp.phoneNumber,
                email = temp.email
            )
        } else if (user is ConfectionerPassword) {
            temp = user.copy(id = current_index++)
            usersDatabase.add(temp)
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
        var filtered =
            usersDatabase.filter { it.phoneNumber == user.phoneNumber && it.id != user.id }
        if (filtered.isNotEmpty()) throw Exception("На этот номер телефона зарегистрирован другой аккаунт")

        filtered = usersDatabase.filter { it.id == user.id }
        if (filtered.isEmpty()) throw Exception("Пользователя не существует")

        val deleted = filtered.last()
        usersDatabase.remove(filtered.last())
        if (deleted is CustomerPassword) {
            if (user is Customer) {
                usersDatabase.add(
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
        } else if (deleted is ConfectionerPassword) {
            if (user is Confectioner) {
                usersDatabase.add(
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

    fun getAllConfectioners(): List<Confectioner> {
        val filtered = ArrayList<Confectioner>()
        for (temp in usersDatabase.filterIsInstance<ConfectionerPassword>()) {
            filtered.add(
                Confectioner(
                    temp.id,
                    temp.name,
                    temp.phoneNumber,
                    temp.email,
                    temp.description,
                    temp.address
                )
            )
        }
        return filtered
    }

    fun getAllConfectionersWithName(name: String): List<Confectioner> {
        val filtered = ArrayList<Confectioner>()
        for (temp in usersDatabase.filterIsInstance<ConfectionerPassword>()
            .filter { it.name.contains(name) || name.contains(it.name) }) {
            filtered.add(
                Confectioner(
                    temp.id,
                    temp.name,
                    temp.phoneNumber,
                    temp.email,
                    temp.description,
                    temp.address
                )
            )
        }
        return filtered
    }
}