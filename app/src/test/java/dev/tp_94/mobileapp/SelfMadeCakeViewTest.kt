package dev.tp_94.mobileapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.self_made_cake.domain.SendCustomCakeUseCase
import dev.tp_94.mobileapp.self_made_cake.presentation.SelfMadeCakeViewModel
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SelfMadeCakeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockUseCase = mockk<SendCustomCakeUseCase>()
    private val mockSessionCache = mockk<SessionCache>()
    private lateinit var viewModel: SelfMadeCakeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        val confectioner = Confectioner(id = 1, name = "Test", phoneNumber = "123", email = "test@test.com", description = "Blalblablabla", address = "Pushkin Home")
        val savedStateHandle = SavedStateHandle().apply {
            set("confectionerJson", Json.encodeToString(Confectioner.serializer(), confectioner))
        }

        viewModel = SelfMadeCakeViewModel(
            savedStateHandle = savedStateHandle,
            sessionCache = mockSessionCache,
            sendCustomCakeUseCase = mockUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `setColor updates state`() {
        // Arrange
        val newColor = Color.Red

        // Act
        viewModel.setColor(newColor)

        // Assert
        assertEquals(newColor, viewModel.state.value.cakeCustom.color)
    }

    @Test
    fun `setDiameter updates state`() {
        // Arrange
        val newDiameter = 15f

        // Act
        viewModel.setDiameter(newDiameter)

        // Assert
        assertEquals(newDiameter, viewModel.state.value.cakeCustom.diameter)
    }


    @Test
    fun `open and close colorPicker updates state`() {
        // Act & Assert
        viewModel.openColorPicker()
        assertTrue(viewModel.state.value.colorPickerOpen)

        viewModel.closeColorPicker()
        assertFalse(viewModel.state.value.colorPickerOpen)
    }
}