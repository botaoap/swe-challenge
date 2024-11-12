package com.gabrielbotao.swechallenge.data.remote.network

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class WebServiceImplTest {
    private lateinit var webService: WebServiceImpl
    private lateinit var httpClient: HttpClient

    @Before
    fun setUp() {
        httpClient = HttpClient(MockEngine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )

            }
            engine {
                addHandler { request ->
                    when (request.url.fullPath) {
                        "/auth/login" -> {
                            respond(
                                content = """{"token":"fake_token"}""",
                                status = HttpStatusCode.OK,
                                headers = headersOf(HttpHeaders.ContentType, "application/json")
                            )
                        }
                        "/products" -> {
                            respond(
                                content = """[{"id":1,"name":"Product 1"}]""",
                                status = HttpStatusCode.OK,
                                headers = headersOf(HttpHeaders.ContentType, "application/json")
                            )
                        }
                        else -> error("Unhandled ${request.url.fullPath}")
                    }
                }
            }
        }
        webService = WebServiceImpl(httpClient)
    }

    @Test
    fun `postLogin sends correct request and returns response`() = runTest {
        // Arrange
        val username = "testuser"
        val password = "testpass"

        // Act
        val response = webService.postLogin(username, password)

        // Assert
        assertEquals(HttpStatusCode.OK, response.status)
        val responseBody: String = response.bodyAsText()
        assertEquals("""{"token":"fake_token"}""", responseBody)
    }

    @Test
    fun `getProducts sends correct request and returns response`() = runTest {
        // Act
        val response = webService.getProducts()

        // Assert
        assertEquals(HttpStatusCode.OK, response.status)
        val responseBody: String = response.bodyAsText()
        assertEquals("""[{"id":1,"name":"Product 1"}]""", responseBody)
    }
}