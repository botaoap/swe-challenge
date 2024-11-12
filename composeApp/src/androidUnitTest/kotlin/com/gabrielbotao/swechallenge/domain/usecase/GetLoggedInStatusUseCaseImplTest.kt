package com.gabrielbotao.swechallenge.domain.usecase

import com.gabrielbotao.swechallenge.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class GetLoggedInStatusUseCaseImplTest {

    private lateinit var getLoggedInStatusUseCase: GetLoggedInStatusUseCase
    private val repository: Repository = mockk()
    private val coroutineDispatcher = Dispatchers.Unconfined

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        // Set the main dispatchers for test
        Dispatchers.setMain(coroutineDispatcher)
        getLoggedInStatusUseCase = GetLoggedInStatusUseCaseImpl(repository, coroutineDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        // Reset the main dispatchers
        Dispatchers.resetMain()
    }

    @Test
    fun `execute should emit Loading and then Success when repository returns logged in status`() = runTest {
        // Arrange
        val isLoggedIn = true
        coEvery { repository.getCurrentLoggedInStatus() } returns flowOf(isLoggedIn)

        // Act
        val results = mutableListOf<LoggedInState>()
        getLoggedInStatusUseCase.execute().collect { results.add(it) }

        // Assert
        assertTrue { results.contains(LoggedInState.Loading) }
        assertTrue { results.contains(LoggedInState.Success(isLoggedIn)) }

        coVerify { repository.getCurrentLoggedInStatus() }
    }

    @Test
    fun `execute should emit Loading and Success when repository returns false`() = runTest {
        // Arrange
        val isLoggedIn = false
        coEvery { repository.getCurrentLoggedInStatus() } returns flowOf(isLoggedIn)

        // Act
        val results = mutableListOf<LoggedInState>()
        getLoggedInStatusUseCase.execute().collect { results.add(it) }

        // Assert
        assertTrue { results.contains(LoggedInState.Loading) }
        assertTrue { results.contains(LoggedInState.Success(isLoggedIn)) }

        coVerify { repository.getCurrentLoggedInStatus() }
    }

    @Test
    fun `execute should emit Error when repository throws exception`() = runTest {
        // Arrange
        coEvery { repository.getCurrentLoggedInStatus() } throws Exception("Test error")

        // Act
        val results = mutableListOf<LoggedInState>()
        getLoggedInStatusUseCase.execute().collect { results.add(it) }

        // Assert
        assertTrue { results.contains(LoggedInState.Error) }

        coVerify { repository.getCurrentLoggedInStatus() }
    }
}