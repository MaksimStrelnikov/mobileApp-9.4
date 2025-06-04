package dev.tp_94.mobileapp.cakes_feed.domain

import dev.tp_94.mobileapp.cakes_feed.data.NameBodyDTO
import dev.tp_94.mobileapp.cakes_feed.presentation.CakeFeedResult
import dev.tp_94.mobileapp.self_made_cake.domain.CakeRepository
import javax.inject.Inject

class LoadMoreCakesUseCase @Inject constructor(
    private val repository: CakeRepository
){
    suspend fun execute(text: String?): CakeFeedResult {
        try {
            val result = if (text.isNullOrEmpty()) {
                repository.getAllGeneral()
            } else {
                repository.getAllByName(NameBodyDTO(text))
            }
            val list = result.map { it.toGeneral() }
            return CakeFeedResult.Success(list)
        } catch (e: Exception) {
            return CakeFeedResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}