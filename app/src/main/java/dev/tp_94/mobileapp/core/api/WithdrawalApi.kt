package dev.tp_94.mobileapp.core.api

import dev.tp_94.mobileapp.withdrawal.data.BalanceResponseDTO
import dev.tp_94.mobileapp.withdrawal.data.WithdrawRequestDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WithdrawalApi {
    @POST("confectioners/self/withdraw")
    suspend fun withdraw(@Body withdrawRequestDTO: WithdrawRequestDTO): Response<BalanceResponseDTO>

    @GET("confectioners/self/balance")
    suspend fun balance(): Response<BalanceResponseDTO>
}