package dev.tp_94.mobileapp.customers_feed.domain

import dev.tp_94.mobileapp.cakes_feed.data.NameBodyDTO
import dev.tp_94.mobileapp.customers_feed.presentation.FeedResult
import dev.tp_94.mobileapp.customers_feed.presentation.Sorting
import javax.inject.Inject

class SortConfectionersUseCase @Inject constructor(
    private val repository: ConfectionerRepository
) {
    suspend fun execute(text: String?, sorting: Sorting): FeedResult {
        try {
            if (sorting == Sorting.NO_SORTING) {
                val result = if (text.isNullOrEmpty()) {
                    repository.getAll()
                } else {
                    repository.getAllByName(NameBodyDTO(text))
                }
                return FeedResult.Success(result.map { it.toConfectioner() })
            } else {
                val result = repository.getAllByNameSorted(NameBodyDTO(text ?: ""), sorting)
                return FeedResult.Success(result.map { it.toConfectioner() })
            }
        } catch (e: Exception) {
            return FeedResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}