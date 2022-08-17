package com.rbt.merchant.domain.data.remote.http_connection.client

import android.util.Log
import com.rbt.merchant.utils.HttpRoutes
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json


/**
 * KtorClient is an Object Class that create the https request
 * https://ktor.io/docs/welcome.html
*/
private const val TAG = "KtorClient"
object KtorClient {

    private const val AUTHORIZATION_HEADER = "Authorization"
    private var API_KEY: String = ""

    private val client = HttpClient(Android) {
        // create https connection
        defaultRequest {
            host = HttpRoutes.BASE_URL_API
            url {
                protocol = URLProtocol.HTTPS
            }
            header(AUTHORIZATION_HEADER, "BEARER $API_KEY")
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                })
        }
        // to check request header before send it
        install(DefaultRequest) {
            // headers.append("Content-Type", "application/json")
            /*Log.d(TAG, "HTTPS request header: $headers")
            Log.d(TAG, "HTTPS request header: ${headers.entries()}")
            Log.d(TAG, "HTTPS request header: set-cookie: ${headers["set-cookie"]}")
            Log.d(TAG, "HTTPS request header: otp: ${headers["set-cookie"]?.subSequence(4,10)}")*/
        }
        // to check response header and value after send it
        install(ResponseObserver) {
            onResponse { response ->
                Log.d(TAG, "HTTPS status: ${response.status.value}")
                /*Log.d(TAG, "HTTPS response header: ${response.headers}")
                Log.d(TAG, "HTTPS response header: ${response.headers.entries()}")
                Log.d(TAG, "HTTPS response header: set-cookie: ${response.headers["set-cookie"]}")
                Log.d(TAG, "HTTPS response header: ${response.headers["set-cookie"]?.subSequence(4,10)}")*/
            }
        }
        // check the response validation and its status code
        HttpResponseValidator {
            validateResponse { response: HttpResponse ->
                val statusCode = response.status.value
                // according to status code for response it will throw its exception
                when (statusCode) {
                    in 300..399 -> throw RedirectResponseException(response)
                    in 400..499 -> throw ClientRequestException(response)
                    in 500..599 -> throw ServerResponseException(response)
                }

                if (statusCode >= 600) {
                    throw ResponseException(response)
                }
            }

            handleResponseException { cause: Throwable ->
                throw cause
            }
        }
    }

    val getInstance = client
}