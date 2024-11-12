package com.gabrielbotao.swechallenge.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.gabrielbotao.swechallenge.constants.DataStoreKey.EMAIL_KEY
import com.gabrielbotao.swechallenge.constants.DataStoreKey.FULL_NAME_KEY
import com.gabrielbotao.swechallenge.constants.DataStoreKey.IS_LOGGED_IN_KEY
import com.gabrielbotao.swechallenge.constants.DataStoreKey.PROFILE_IMAGE_KEY
import com.gabrielbotao.swechallenge.constants.DataStoreKey.USERNAME_KEY
import com.gabrielbotao.swechallenge.data.remote.api.WebService
import com.gabrielbotao.swechallenge.domain.model.LoginModel
import com.gabrielbotao.swechallenge.domain.repository.Repository
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val webService: WebService,
    private val dataStore: DataStore<Preferences>
) : Repository {

    override suspend fun getCurrentLoggedInStatus(): Flow<Boolean> =
        run {
            dataStore.data.map { preferences ->
                preferences[IS_LOGGED_IN_KEY] ?: false
            }
        }

    override suspend fun saveLoggedInStatus(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN_KEY] = isLoggedIn
        }
    }

    override suspend fun getCurrentUserData(): Flow<LoginModel> =
        run {
            dataStore.data.map { preferences ->
                LoginModel(
                    username = preferences[USERNAME_KEY] ?: "",
                    fullName = preferences[FULL_NAME_KEY] ?: "",
                    email = preferences[EMAIL_KEY] ?: "",
                    image = preferences[PROFILE_IMAGE_KEY] ?: ""
                )
            }
        }

    override suspend fun saveUserData(data: LoginModel) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = data.username
            preferences[FULL_NAME_KEY] = data.fullName
            preferences[EMAIL_KEY] = data.email
            preferences[PROFILE_IMAGE_KEY] = data.image
        }
    }

    override suspend fun postLogin(username: String, password: String): HttpResponse =
        webService.postLogin(
            username = username,
            password = password
        )

    override suspend fun getProducts(): HttpResponse =
        webService.getProducts()
}