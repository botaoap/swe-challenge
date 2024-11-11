package com.gabrielbotao.swechallenge.domain.repository

import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow

interface Repository {
    /** This function get status of is_logged_in key from DataStore */
    suspend fun getCurrentLoggedInStatus(): Flow<Boolean>

    /** This function save status of is_logged_in key in DataStore */
    suspend fun saveLoggedInStatus(isLoggedIn: Boolean)

    /** This function login in app */
    suspend fun postLogin(
        username: String,
        password: String
    ) : HttpResponse

    /** This function get list of products */
    suspend fun getProducts(): HttpResponse
}