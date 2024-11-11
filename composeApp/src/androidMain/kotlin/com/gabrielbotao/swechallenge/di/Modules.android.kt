package com.gabrielbotao.swechallenge.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.gabrielbotao.swechallenge.data.local.DATA_STORE_FILE_NAME
import com.gabrielbotao.swechallenge.data.local.createDataStore
import com.gabrielbotao.swechallenge.data.remote.api.WebService
import com.gabrielbotao.swechallenge.data.remote.network.WebServiceImpl
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.dsl.module
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

actual val platformModule = module {
    single { providerDataStore(get()) }
    single<WebService> {
        WebServiceImpl(
            HttpClient(OkHttp) {
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                        }
                    )
                }

                engine {
                    preconfigured = OkHttpClient.Builder()
                        .sslSocketFactory(createInsecureSslSocketFactory(), createInsecureTrustManager())
                        .hostnameVerifier { _, _ -> true }
                        .build()
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

private fun providerDataStore(context: Context): DataStore<Preferences> =
    createDataStore {
        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
    }

/**
 * Work around to get certificate in Okhttp engine
 * Do not do this in prod environment
 */
private fun createInsecureTrustManager(): X509TrustManager = object : X509TrustManager {
    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {}

    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {}

    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
}

private fun createInsecureSslSocketFactory(): SSLSocketFactory {
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, arrayOf(createInsecureTrustManager()), java.security.SecureRandom())
    return sslContext.socketFactory
}