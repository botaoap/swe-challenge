package com.gabrielbotao.swechallenge.domain.usecase

import com.gabrielbotao.swechallenge.domain.model.LoginModel
import com.gabrielbotao.swechallenge.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertTrue

class GetUserDataUseCaseImplTest {

    private lateinit var getUserDataUseCase: GetUserDataUseCaseImpl
    private val repository: Repository = mockk()
    private val coroutineDispatcher = Dispatchers.Unconfined

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        // Set the main dispatchers for test
        Dispatchers.setMain(coroutineDispatcher)
        getUserDataUseCase = GetUserDataUseCaseImpl(repository, coroutineDispatcher)
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
        coEvery { repository.getCurrentUserData() } returns flowOf(mockk())

        // Act
        val results = mutableListOf<UserDataState>()
        results.add(getUserDataUseCase.execute().first())

        // Assert
        assertTrue { results.contains(UserDataState.Loading) }

        coVerify { repository.getCurrentUserData() }
    }

    @Test
    fun `execute emits Success state with data`() = runTest {
        // Arrange
        val loginModel = LoginModel("username", "full name", "email@example.com", "image_url")
        coEvery { repository.getCurrentUserData() } returns flowOf(loginModel)

        // Act
        val results = mutableListOf<UserDataState>()
        getUserDataUseCase.execute().collect { results.add(it) }

        // Assert
        assertTrue { results.contains(UserDataState.Loading) }
        assertTrue { results.contains(UserDataState.Success(loginModel)) }

        coVerify { repository.getCurrentUserData() }
    }

    @Test
    fun `execute emits Error state when exception is thrown`() = runTest {
        // Arrange
        coEvery { repository.getCurrentUserData() } throws Exception("Network error")

        // Act
        val results = mutableListOf<UserDataState>()
        getUserDataUseCase.execute().collect { results.add(it) }

        // Assert
        assertTrue { results.contains(UserDataState.Loading) }
        assertTrue { results.contains(UserDataState.Error) }

        coVerify { repository.getCurrentUserData() }
    }
}