package com.gabrielbotao.swechallenge.domain.usecase

import com.gabrielbotao.swechallenge.domain.mapper.LoginMapper
import com.gabrielbotao.swechallenge.domain.model.LoginModel
import com.gabrielbotao.swechallenge.domain.repository.Repository
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import kotlin.test.Test
import kotlin.test.assertTrue

class PostLoginUseCaseImplTest {
    private lateinit var postLoginUseCase: PostLoginUseCase
    private val repository: Repository = mockk()
    private val mapper: LoginMapper = mockk()
    private val coroutineDispatcher = Dispatchers.Unconfined

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        // Set the main dispatchers for test
        Dispatchers.setMain(coroutineDispatcher)
        postLoginUseCase = PostLoginUseCaseImpl(repository, mapper, coroutineDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        // Reset the main dispatchers
        Dispatchers.resetMain()
    }

    @Test
    fun `execute emits Loading state initially`() = runTest {
        // Arrange
        coEvery { repository.postLogin(any(), any()) } returns mockk()

        // Act
        val results = mutableListOf<LoginState>()
        results.add(postLoginUseCase.execute("username", "password").first())

        // Assert
        assertTrue { results.contains(LoginState.Loading) }

        coVerify { repository.postLogin("username", "password") }
    }

    /**
     * This test not working yet
     * Need to mock body correctly
     */
    @Ignore
    @Test
    fun `execute emits Success state with data when response is 200`() = runTest {
        // Arrange
        val loginModel = LoginModel("username", "full name", "email@example.com", "image_url")
        val response: HttpResponse = mockk {
            every { headers } returns mockk(relaxed = true)
            every { status.value } returns 200
            coEvery { body<LoginModel>() } returns loginModel
        }
        coEvery { repository.postLogin(any(), any()) } returns response
        every { mapper.getLogin(any()) } returns loginModel
        coEvery { repository.saveUserData(any()) } just Runs

        // Act
        val results = mutableListOf<LoginState>()
        postLoginUseCase.execute("username", "password").collect { results.add(it) }

        // Assert
        assertTrue { results.contains(LoginState.Loading) }
        assertTrue { results.contains(LoginState.Success(loginModel)) }

        coVerify { repository.postLogin("username", "password") }
        coVerify { repository.saveUserData(loginModel) }
    }

    @Test
    fun `execute emits Error state when response is 404`() = runTest {
        // Arrange
        val response: HttpResponse = mockk {
            every { headers } returns mockk(relaxed = true)
            every { status.value } returns 404
        }
        coEvery { repository.postLogin(any(), any()) } returns response

        // Act
        val results = mutableListOf<LoginState>()
        postLoginUseCase.execute("username", "password").collect { results.add(it) }

        // Assert
        assertTrue { results.contains(LoginState.Loading) }
        assertTrue { results.contains(LoginState.Error) }

        coVerify { repository.postLogin("username", "password") }
    }

    @Test
    fun `execute emits Error state when exception is thrown`() = runTest {
        // Arrange
        coEvery { repository.postLogin(any(), any()) } throws Exception("Network error")

        // Act
        val results = mutableListOf<LoginState>()
        postLoginUseCase.execute("username", "password").collect { results.add(it) }

        // Assert
        assertTrue { results.contains(LoginState.Loading) }
        assertTrue { results.contains(LoginState.Error) }

        coVerify { repository.postLogin("username", "password") }
    }
}