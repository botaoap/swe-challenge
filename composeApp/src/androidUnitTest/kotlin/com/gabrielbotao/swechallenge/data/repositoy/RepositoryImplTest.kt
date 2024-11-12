package com.gabrielbotao.swechallenge.data.repositoy

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.gabrielbotao.swechallenge.constants.DataStoreKey
import com.gabrielbotao.swechallenge.data.remote.api.WebService
import com.gabrielbotao.swechallenge.data.repository.RepositoryImpl
import com.gabrielbotao.swechallenge.domain.model.LoginModel
import io.ktor.client.statement.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Ignore
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RepositoryImplTest {

    private lateinit var webService: WebService
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var repository: RepositoryImpl

    @BeforeTest
    fun setup() {
        // Create mocks
        webService = mockk()
        dataStore = mockk()

        // Initialized repository
        repository = RepositoryImpl(webService, dataStore)
    }

    @Test
    fun `getCurrentLoggedInStatus returns login status from DataStore`() = runTest {
        // Arrange
        val loggedInStatus = true
        val preferences = mockk<Preferences> {
            every { get(DataStoreKey.IS_LOGGED_IN_KEY) } returns loggedInStatus
        }
        every { dataStore.data } returns flowOf(preferences)

        // Act
        val result = repository.getCurrentLoggedInStatus().first()

        // Assert
        assertEquals(loggedInStatus, result)
    }

    /**
     * This test not working yet
     * Need to mock dataStore.edit() correctly
     */
    @Ignore
    @Test
    fun `saveLoggedInStatus saves login status to DataStore`() = runTest {
        // Arrange
        val loggedInStatus = true
        val mockPreferences = mockk<Preferences>(relaxed = true)
        coEvery { dataStore.edit(any()) } coAnswers {
            val editFunction = it.invocation.args[0] as (MutablePreferences) -> Unit
            val mutablePreferences = mockk<MutablePreferences>(relaxed = true)
            editFunction(mutablePreferences)
            mockPreferences
        }

        // Act
        repository.saveLoggedInStatus(loggedInStatus)

        // Assert
        coVerify {
            dataStore.edit {
                it[DataStoreKey.IS_LOGGED_IN_KEY] = loggedInStatus
            }
        }
    }


    @Test
    fun `getCurrentUserData returns correct user data`() = runTest {
        // Arrange
        val preferences = mockk<Preferences> {
            every { this@mockk[DataStoreKey.USERNAME_KEY] } returns "username"
            every { this@mockk[DataStoreKey.FULL_NAME_KEY] } returns "full name"
            every { this@mockk[DataStoreKey.EMAIL_KEY] } returns "email@example.com"
            every { this@mockk[DataStoreKey.PROFILE_IMAGE_KEY] } returns "image_url"
        }
        every { dataStore.data } returns flowOf(preferences)

        // Act
        val result = repository.getCurrentUserData()

        // Assert
        result.collect { userData ->
            assert(userData.username == "username")
            assert(userData.fullName == "full name")
            assert(userData.email == "email@example.com")
            assert(userData.image == "image_url")
        }
    }

    /**
     * This test not working yet
     * Need to mock dataStore.edit() correctly
     */
    @Ignore
    @Test
    fun `saveUserData saves user data to DataStore`() = runTest {
        // Arrange
        val userData = LoginModel(
            username = "username",
            fullName = "full name",
            email = "email@example.com",
            image = "image_url"
        )
        coEvery { dataStore.edit(any()) } coAnswers {
            val editFunction = it.invocation.args[0] as (MutablePreferences) -> Unit
            val mockPreferences = mockk<MutablePreferences>(relaxed = true)
            editFunction(mockPreferences)
            mockPreferences
        }

        // Act
        repository.saveUserData(userData)

        // Assert
        coVerify {
            dataStore.edit {
                it[DataStoreKey.USERNAME_KEY] = userData.username
                it[DataStoreKey.FULL_NAME_KEY] = userData.fullName
                it[DataStoreKey.EMAIL_KEY] = userData.email
                it[DataStoreKey.PROFILE_IMAGE_KEY] = userData.image
            }
        }
    }

    @Test
    fun `postLogin calls webService with correct parameters`() = runTest {
        // Arrange
        val username = "username"
        val password = "password"
        val response: HttpResponse = mockk()
        coEvery { webService.postLogin(username, password) } returns response

        // Act
        val result = repository.postLogin(username, password)

        // Assert
        assert(result == response)
        coVerify { webService.postLogin(username, password) }
    }


    @Test
    fun `getProducts calls webService and returns response`() = runTest {
        // Arrange
        val response: HttpResponse = mockk()
        coEvery { webService.getProducts() } returns response

        // Act
        val result = repository.getProducts()

        // Assert
        assert(result == response)
        coVerify { webService.getProducts() }
    }
}
