package dev.tp_94.mobileapp.signup.domain

import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.login.domain.UserRepository
import dev.tp_94.mobileapp.signup.presenatation.SignUpResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SignUpUseCaseTest {

    private val mockRepository = mockk<UserRepository>()
    private lateinit var signUpUseCase: SignUpUseCase

    @Before
    fun setup() {
        signUpUseCase = SignUpUseCase(mockRepository)
    }

    @Test
    fun `execute with invalid phone returns error`() = runTest {
        val result = signUpUseCase.execute(
            phoneNumber = "123", // invalid
            password = "password",
            name = "Name",
            email = "valid@example.com", // valid email
            isConfectioner = false
        )

        assertTrue(result is SignUpResult.Error)
        assertEquals("Некорректный формат номера телефона", (result as SignUpResult.Error).message)
    }

    @Test
    fun `empty password returns error`() = runTest {
        val result = signUpUseCase.execute(
            phoneNumber = "1234567890",
            password = "",
            name = "Name",
            email = "test@example.com",
            isConfectioner = false
        )

        assertTrue(result is SignUpResult.Error)
        assertEquals("Пароль не указан", (result as SignUpResult.Error).message)
    }
}


