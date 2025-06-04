package dev.tp_94.mobileapp.customers_feed.domain

import dev.tp_94.mobileapp.cakes_feed.data.NameBodyDTO
import dev.tp_94.mobileapp.customers_feed.presentation.FeedResult
import dev.tp_94.mobileapp.orders.data.dto.ConfectionerResponseDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadMoreConfectionersUseCase @Inject constructor(private val repository: ConfectionerRepository) {
    suspend fun execute(text: String?): FeedResult {
        try {
            val result = if (text.isNullOrEmpty()) {
                repository.getAll()
            } else {
                repository.getAllByName(NameBodyDTO(text))
            }
            val list = result.map { it.toConfectioner() }
            return FeedResult.Success(list)
        } catch (e: Exception) {
            return FeedResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}