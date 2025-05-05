package dev.tp_94.mobileapp

import androidx.compose.ui.graphics.Color
import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.selfmadecake.domain.OrderRepository
import dev.tp_94.mobileapp.selfmadecake.domain.SendCustomCakeUseCase
import dev.tp_94.mobileapp.selfmadecake.presentation.SelfMadeCakeResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SendCustomCakeUseCaseTest {

    private val mockRepository = mockk<OrderRepository>()
    private lateinit var useCase: SendCustomCakeUseCase

    @Before
    fun setup() {
        useCase = SendCustomCakeUseCase(mockRepository)
    }

    @Test
    fun `execute with valid data returns success`() = runTest {
        // Arrange
        val cake = CakeCustom(Color.Cyan, 10f)
        val customer = Customer(id = 1, name = "Test", phoneNumber = "123", email = "test@test.com")
        val confectioner = Confectioner(id = 1, name = "Conf", phoneNumber = "456", email = "conf@test.com", description = "Blalblablabla", address = "Pushkin Home")

        coEvery {
            mockRepository.placeCustomCakeOrder(cake, customer, confectioner)
        } returns Unit

        // Act
        val result = useCase.execute(cake, customer, confectioner)

        // Assert
        assertTrue(result is SelfMadeCakeResult.Success)
    }

    @Test
    fun `execute with repository error returns error`() = runTest {
        // Arrange
        val cake = CakeCustom(Color.Cyan, 10f)
        val customer = Customer(id = 1, name = "Test", phoneNumber = "123", email = "test@test.com")
        val confectioner = Confectioner(id = 1, name = "Conf", phoneNumber = "456", email = "conf@test.com", description = "Blalblablabla", address = "Pushkin Home")

        coEvery {
            mockRepository.placeCustomCakeOrder(any(), any(), any())
        } throws RuntimeException("Database error")

        // Act
        val result = useCase.execute(cake, customer, confectioner)

        // Assert
        assertTrue(result is SelfMadeCakeResult.Error)
        assertEquals("Database error", (result as SelfMadeCakeResult.Error).message)
    }
}