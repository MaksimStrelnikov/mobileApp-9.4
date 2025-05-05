package dev.tp_94.mobileapp

import dev.tp_94.mobileapp.login.domain.LoginUseCase
import dev.tp_94.mobileapp.login.domain.UserRepository
import dev.tp_94.mobileapp.login.presentation.LoginResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    private val mockRepository = mockk<UserRepository>()
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        loginUseCase = LoginUseCase(mockRepository)
    }

    @Test
    fun `when phone number is not 10 digits, returns error`() = runTest {
        val result = loginUseCase.execute("123", "validPass")
        
        assertTrue(result is LoginResult.Error)
        assertEquals(
            "Некорректный формат номера телефона",
            (result as LoginResult.Error).message
        )
    }

    @Test
    fun `when password is empty, returns error`() = runTest {
        val result = loginUseCase.execute("1234567890", "")

        assertTrue(result is LoginResult.Error)
        assertEquals(
            "Поле ввода пароля пустое",
            (result as LoginResult.Error).message
        )
    }


    @Test
    fun `when repository throws exception, returns Error`() = runTest {
        coEvery {
            mockRepository.login("1234567890", "validPass")
        } throws RuntimeException("Auth failed")

        val result = loginUseCase.execute("1234567890", "validPass")

        assertTrue(result is LoginResult.Error)
        assertEquals("Auth failed", (result as LoginResult.Error).message)
    }
}