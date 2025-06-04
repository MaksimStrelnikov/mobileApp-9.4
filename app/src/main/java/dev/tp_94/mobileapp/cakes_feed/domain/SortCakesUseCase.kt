package dev.tp_94.mobileapp.cakes_feed.domain

import dev.tp_94.mobileapp.cakes_feed.data.NameBodyDTO
import dev.tp_94.mobileapp.cakes_feed.presentation.CakeFeedResult
import dev.tp_94.mobileapp.cakes_feed.presentation.Sorting
import dev.tp_94.mobileapp.self_made_cake.domain.CakeRepository
import javax.inject.Inject

class SortCakesUseCase @Inject constructor(
    private val repository: CakeRepository
) {
    suspend fun execute(text: String?, sorting: Sorting): CakeFeedResult {
        try {
            if (sorting == Sorting.NO_SORTING) {
                val result = if (text.isNullOrEmpty()) {
                    repository.getAllGeneral()
                } else {
                    repository.getAllByName(NameBodyDTO(text))
                }
                return CakeFeedResult.Success(result.map { it.toGeneral() })
            } else {
                val result = repository.getAllByNameSorted(NameBodyDTO(text ?: ""), sorting)
                return CakeFeedResult.Success(result.map { it.toGeneral() })
            }
        } catch (e: Exception) {
            return CakeFeedResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}

