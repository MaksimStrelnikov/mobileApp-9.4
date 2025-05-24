package dev.tp_94.mobileapp.profile.domain

import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.profile.presentation.DeleteAccountResult
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
) {
    suspend fun execute(
        user: User,
    ): DeleteAccountResult {
        try {
            /*TODO: tokenization - delete from the bd here*/
            return DeleteAccountResult.Success()
        } catch (e: Exception) {
            return DeleteAccountResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}