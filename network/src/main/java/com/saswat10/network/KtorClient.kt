package com.saswat10.network

import com.saswat10.network.models.remote.Registration
import com.saswat10.network.models.remote.RegistrationResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient {
    private val client = HttpClient(OkHttp) {
        defaultRequest { url("https://posthive-api.onrender.com/") }

        install(Logging) {
            logger = Logger.SIMPLE
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys= true
                prettyPrint= true
                isLenient = true
            })
        }

//        install(Auth){
//            bearer {
//                refreshTokens {  }
//            }
//        }
    }


    suspend fun register(data: Registration): ApiOperation<RegistrationResponse> {
        return safeApiCall {
            client.post("users/"){
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body()
        }
    }

    suspend fun register2(data: Registration): RegistrationResponse {
           return client.post("users/"){
               contentType(ContentType.Application.Json)
               setBody(data)
           }.body()
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): ApiOperation<T> {
        return try {
            ApiOperation.Success(data = apiCall())
        } catch (e: Exception) {
            ApiOperation.Failure(e = e)
        }
    }


}

sealed interface ApiOperation<T> {
    data class Success<T>(val data: T) : ApiOperation<T>
    data class Failure<T>(val e: Exception) : ApiOperation<T>

    fun <R> mapSuccess(transform: (T) -> R): ApiOperation<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Failure -> Failure(e)
        }
    }

    suspend fun onSuccess(block: suspend (T) -> Unit): ApiOperation<T> {
        if (this is Success) block(data)
        return this
    }

    suspend fun onFailure(block: suspend (Exception) -> Unit): ApiOperation<T> {
        if (this is Failure) block(e)
        return this
    }
}