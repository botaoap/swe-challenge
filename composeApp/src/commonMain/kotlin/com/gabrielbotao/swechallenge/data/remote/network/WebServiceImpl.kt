package com.gabrielbotao.swechallenge.data.remote.network

import com.gabrielbotao.swechallenge.constants.UrlProvider
import com.gabrielbotao.swechallenge.data.remote.LoginRequest
import com.gabrielbotao.swechallenge.data.remote.api.WebService
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class WebServiceImpl(
    private val httpClient: HttpClient
) : WebService {

    override suspend fun postLogin(
        username: String,
        password: String
    ): HttpResponse =
        httpClient.post(
            urlString = "${UrlProvider.BASE_URL}/auth/login"
        ) {
            contentType(ContentType.Application.Json)
            headers {
                append(HttpHeaders.ContentType, "application/json")
            }
            setBody(
                LoginRequest(username, password, 30)
            )
        }

    override suspend fun getProducts(): HttpResponse =
        httpClient.get(
            urlString = "${UrlProvider.BASE_URL}/products"
        )
}