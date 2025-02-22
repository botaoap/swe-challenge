@file:OptIn(ExperimentalForeignApi::class)

package com.gabrielbotao.swechallenge.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.gabrielbotao.swechallenge.data.local.DATA_STORE_FILE_NAME
import com.gabrielbotao.swechallenge.data.local.createDataStore
import com.gabrielbotao.swechallenge.data.remote.api.WebService
import com.gabrielbotao.swechallenge.data.remote.network.WebServiceImpl
import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURLSession
import platform.Foundation.NSURLSessionConfiguration
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val platformModule = module {
    single<DataStore<Preferences>> {
        createDataStore {
            val directory = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            )
            requireNotNull(directory).path() + "/$DATA_STORE_FILE_NAME"
        }
    }
    single<WebService> {
        WebServiceImpl(
//            createHttpClient(Darwin.create())
            HttpClient(Darwin) {
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                        }
                    )
                }
                engine {
                    configureRequest {
//                        setNSURLSession(createSession())
                    }
                }
                /**
                 * For log request
                 */
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.INFO
                }
            }
        )
    }
}

// Assuming you have a method to create the session
//fun createSession(): NSURLSession {
//    val configuration = NSURLSessionConfiguration.defaultSessionConfiguration()
//    val delegate = CustomSessionDelegate() // This is your Swift class
//    return NSURLSession.sessionWithConfiguration(configuration, delegate, null)
//}