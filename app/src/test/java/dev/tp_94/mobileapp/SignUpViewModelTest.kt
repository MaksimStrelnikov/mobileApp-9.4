package dev.tp_94.mobileapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.signup.domain.SignUpUseCase
import dev.tp_94.mobileapp.signup.presenatation.SignUpResult
import dev.tp_94.mobileapp.signup.presenatation.SignUpViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SignUpViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockUseCase = mockk<SignUpUseCase>()
    private lateinit var viewModel: SignUpViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SignUpViewModel(mockUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updatePhoneNumber updates state`() {
        viewModel.updatePhoneNumber("1234567890")
        assertEquals("1234567890", viewModel.state.value.phoneNumber)
    }

    @Test
    fun `updatePassword updates state`() {
        viewModel.updatePassword("password123")
        assertEquals("password123", viewModel.state.value.password)
    }

    @Test
    fun `updateName updates state`() {
        viewModel.updateName("Test User")
        assertEquals("Test User", viewModel.state.value.name)
    }

    @Test
    fun `updateEmail updates state`() {
        viewModel.updateEmail("test@example.com")
        assertEquals("test@example.com", viewModel.state.value.email)
    }

    @Test
    fun `updateConfectioner updates state`() {
        viewModel.updateConfectioner(true)
        assertTrue(viewModel.state.value.isConfectioner)
    }

    @Test
    fun `signUp with confectioner success calls correct callback`() = runTest {
        val testConfectioner = Confectioner(
            id = 1,
            name = "Test Confectioner",
            phoneNumber = "1234567890",
            email = "test@example.com",
            description = "Test description",
            address = "Test address"
        )

        coEvery {
            mockUseCase.execute(any(), any(), any(), any(), any())
        } returns SignUpResult.Success(testConfectioner)

        var customerCalled = false
        var confectionerCalled = false

        viewModel.updateConfectioner(true)
        viewModel.signUp(
            onSuccessCustomer = { customerCalled = true },
            onSuccessConfectioner = { confectionerCalled = true }
        )

        assertFalse(customerCalled)
        assertTrue(confectionerCalled)
    }

    @Test
    fun `signUp with error updates state`() = runTest {
        coEvery {
            mockUseCase.execute(any(), any(), any(), any(), any())
        } returns SignUpResult.Error("Invalid data")

        viewModel.signUp({}, {})

        assertFalse(viewModel.state.value.isLoading)
        assertEquals("Invalid data", viewModel.state.value.error)
    }
}