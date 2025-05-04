package dev.tp_94.mobileapp.customersfeed.domain

import dev.tp_94.mobileapp.customersfeed.presentation.FeedResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchForConfectionersUseCase @Inject constructor(private val repository: ConfectionerRepository) {
    suspend fun execute(text: String?): FeedResult {
        try {
            if (text.isNullOrEmpty()) {
                val confectioners = repository.getAll()
                return FeedResult.Success(confectioners)
            } else {
                val confectioners = repository.getAllByName(text)
                return FeedResult.Success(confectioners)
            }
        } catch (e: Exception) {
            return FeedResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}