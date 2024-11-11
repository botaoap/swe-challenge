package com.gabrielbotao.swechallenge.data.remote.api

import io.ktor.client.statement.*

interface WebService {

    /**
     * @see <a href="https://dummyjson.com/docs/auth#auth-login">Login documentation</href>
     */
    suspend fun postLogin(
        username: String,
        password: String
    ): HttpResponse

    /**
     * @see <a href="https://dummyjson.com/docs/products#products-all">Products documentation</href>
     */
    suspend fun getProducts(): HttpResponse
}