package dev.tp_94.mobileapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.login.domain.LoginUseCase
import dev.tp_94.mobileapp.login.presentation.LoginResult
import dev.tp_94.mobileapp.login.presentation.LoginViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockSessionCache= mockk<SessionCache>()
    private val mockUseCase = mockk<LoginUseCase>()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(mockSessionCache, mockUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updatePhoneNumber updates state correctly`() {
        viewModel.updatePhoneNumber("1234567890")

        assertEquals("1234567890", viewModel.state.value.phoneNumber)
    }


    @Test
    fun `login with error shows error message`() = runTest {
        coEvery {
            mockUseCase.execute("1234567890", "wrongPass")
        } returns LoginResult.Error("Invalid password")

        viewModel.updatePhoneNumber("1234567890")
        viewModel.updatePassword("wrongPass")
        viewModel.login({}, {})

        assertEquals("Invalid password", viewModel.state.value.error)
        assertFalse(viewModel.state.value.isLoading)
    }
}