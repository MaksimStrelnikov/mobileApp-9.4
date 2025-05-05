package dev.tp_94.mobileapp.data

import android.util.Log
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.ConfectionerPassword
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.CustomerPassword
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.core.models.UserPassword
import dev.tp_94.mobileapp.core.models.toDto
import dev.tp_94.mobileapp.login.data.ConfectionerResponseDTO
import dev.tp_94.mobileapp.login.data.CustomerResponseDTO
import dev.tp_94.mobileapp.login.data.UserApi
import dev.tp_94.mobileapp.login.data.UserResponseDTO
import dev.tp_94.mobileapp.login.domain.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val sessionCache: SessionCache,
    private val api: UserApi
) : UserRepository {
    override suspend fun login(username: String, password: String): User {
        val userResponse = api.getUserByPhone(username)
        if (userResponse.body() == null) throw Exception("Пользователь не найден")
        if (userResponse.body()?.password == password) {
            if (userResponse.body()?.confectioner != null) {
                val response = Confectioner(
                    id = userResponse.body()!!.confectioner.id,
                    name = userResponse.body()!!.name,
                    phoneNumber = userResponse.body()!!.phone_number,
                    email = userResponse.body()!!.email,
                    description = userResponse.body()!!.confectioner.description,
                    address = userResponse.body()!!.confectioner.address
                )
                sessionCache.saveSession(
                    Session(
                        user = response,
                        token = "TODO()"
                    )
                )
                return response
            }
            if (userResponse.body()?.customer != null) {
                val response = Customer(
                    id = userResponse.body()!!.customer.id,
                    name = userResponse.body()!!.name,
                    phoneNumber = userResponse.body()!!.phone_number,
                    email = userResponse.body()!!.email
                )
                sessionCache.saveSession(
                    Session(
                        user = response,
                        token = "TODO()"
                    )
                )
                return response
            }
            throw Exception("Пользователь не имеет роли")
        }
        throw Exception("Неверный пароль")
    }

    override suspend fun add(user: UserPassword): User {
        val userResponse = api.postUser(user.toDto())
        if (userResponse.code() == 404) {
            throw Exception("Нет сети")
        }
        if (userResponse.code() == 201) {
            val userGot = userResponse.body()!!
            Log.println(Log.INFO, "Log", user.toString())
            if (user is ConfectionerPassword) {
                val confectioner = api.postConfectioner(user.toDto(userGot.id))
                val response = Confectioner(
                    id = confectioner.id,
                    name = userGot.name,
                    phoneNumber = userGot.phone_number,
                    email = userGot.email,
                    description = confectioner.description,
                    address = confectioner.address
                )
                sessionCache.saveSession(
                    Session(
                        user = response,
                        token = "TODO()"
                    )
                )
                return response
            } else if (user is CustomerPassword) {
                val customer = api.postCustomer(user.toDto(userGot.id))
                val response = Customer(
                    id = customer.id,
                    name = userGot.name,
                    phoneNumber = userGot.phone_number,
                    email = userGot.email
                )
                sessionCache.saveSession(
                    Session(
                        user = response,
                        token = "TODO()"
                    )
                )
                return response
            }
            throw Exception("User is neither Confectioner nor Customer")
        }
        throw Exception("Unexpected Error")
    }

    override suspend fun update(user: User): User {
        val userResponse = api.getUserByPhone(user.phoneNumber)
        val updateResponse = api.updateUser(
            user.id, UserResponseDTO(
                id = userResponse.body()!!.id,
                email = user.email,
                name = user.email,
                password = userResponse.body()!!.password,
                phone_number = user.phoneNumber,
                confectioner = userResponse.body()!!.confectioner,
                customer = userResponse.body()!!.customer
            )
        )
        if (updateResponse.code() == 404) {
            //TODO
            throw Exception("Response is not good")
        }
        if (user is Confectioner) {
            val updateConfectionerResponse = api.updateConfectioner(
                userId = user.id,
                confectionerResponseDTO = ConfectionerResponseDTO(
                    id = user.id,
                    user_id = userResponse.body()!!.id,
                    description = user.description,
                    address = user.address
                ),
            )
            val response = Confectioner(
                id = updateConfectionerResponse.body()!!.id,
                name = updateResponse.body()!!.name,
                phoneNumber = updateResponse.body()!!.phone_number,
                email = updateResponse.body()!!.email,
                description = updateConfectionerResponse.body()!!.description,
                address = updateConfectionerResponse.body()!!.address
            )
            sessionCache.saveSession(
                Session(
                    user = response,
                    token = "TODO()"
                )
            )
            return response
        } else if (user is Customer) {
            val updateCustomerResponse = api.updateCustomer(
                userId = user.id,
                customerResponseDTO = CustomerResponseDTO(
                    id = user.id,
                    user_id = userResponse.body()!!.id
                ),
            )
            val response = Customer(
                id = updateCustomerResponse.body()!!.id,
                name = updateResponse.body()!!.name,
                phoneNumber = updateResponse.body()!!.phone_number,
                email = updateResponse.body()!!.email
            )
            sessionCache.saveSession(
                Session(
                    user = response,
                    token = "TODO()"
                )
            )
            return response
        }
        throw Exception("User is neither Confectioner nor Customer: " + updateResponse.code())
    }
}