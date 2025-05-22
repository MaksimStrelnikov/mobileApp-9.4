package dev.tp_94.mobileapp.profile_editor.domain

import android.util.Patterns
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.login.domain.UserRepository
import dev.tp_94.mobileapp.profile_editor.presentation.SaveResult
import javax.inject.Inject

class ProfileEditorCustomerChangeUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val sc: SessionCache
) {
    suspend fun execute(name: String, phoneNumber: String, email: String): SaveResult {
        if (sc.session == null || sc.session!!.user !is Customer) {
            throw Exception("Has No Rights To Change Customer Profile")
        }
        if (name.isEmpty()) {
            return SaveResult.Error("Имя не указано")
        }
        if (!(phoneNumber.matches(Regex("[0-9]*")) && phoneNumber.length == 10)) {
            return SaveResult.Error("Некорректный формат номера телефона")
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return SaveResult.Error("Некорректный формат адреса электронной почты")
        }
        try {
            val user = userRepository.update(
                Customer(
                    id = sc.session!!.user.id,
                    name = name,
                    phoneNumber = phoneNumber,
                    email = email
                )
            )
            return SaveResult.Success(user)
        } catch (e: Exception) {
            return SaveResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}