package com.gabrielbotao.swechallenge.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.gabrielbotao.swechallenge.data.remote.api.WebService
import com.gabrielbotao.swechallenge.domain.repository.Repository
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val webService: WebService,
    private val dataStore: DataStore<Preferences>
) : Repository {
    private companion object {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    override suspend fun getCurrentLoggedInStatus(): Flow<Boolean> =
        run {
            dataStore.data.map { preferences ->
                preferences[IS_LOGGED_IN] ?: false
            }
        }

    override suspend fun saveLoggedInStatus(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }

    override suspend fun postLogin(username: String, password: String): HttpResponse =
        webService.postLogin(
            username = username,
            password = password
        )
}