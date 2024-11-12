package com.gabrielbotao.swechallenge.domain.usecase

import com.gabrielbotao.swechallenge.domain.repository.Repository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test

class SaveLoggedInStatusUseCaseImplTest {

    private lateinit var saveLoggedInStatusUseCase: SaveLoggedInStatusUseCaseImpl
    private val repository: Repository = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        // Set the main dispatchers for test
        Dispatchers.setMain(Dispatchers.Unconfined)
        saveLoggedInStatusUseCase = SaveLoggedInStatusUseCaseImpl(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        // Reset the main dispatchers
        Dispatchers.resetMain()
    }

    @Test
    fun `execute calls saveLoggedInStatus on repository`() = runTest {
        // Arrange
        val isLoggedIn = true
        coEvery { repository.saveLoggedInStatus(isLoggedIn) } just Runs

        // Act
        saveLoggedInStatusUseCase.execute(isLoggedIn)

        // Assert
        coVerify { repository.saveLoggedInStatus(isLoggedIn) }
    }
}