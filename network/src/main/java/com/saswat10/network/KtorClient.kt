package com.saswat10.network

import com.saswat10.network.models.domain.Comment
import com.saswat10.network.models.domain.Post
import com.saswat10.network.models.remote.Registration
import com.saswat10.network.models.remote.RegistrationResponse
import com.saswat10.network.models.remote.RemoteComment
import com.saswat10.network.models.remote.RemotePost
import com.saswat10.network.models.remote.toComment
import com.saswat10.network.models.remote.toPost
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.InternalAPI
import kotlinx.serialization.json.Json

class KtorClient {
    private val client = HttpClient(OkHttp) {
        defaultRequest { url("https://posthive-api.onrender.com/") }
        install(HttpTimeout){
            requestTimeoutMillis = 30000 // Total timeout for the request (30 seconds)
            connectTimeoutMillis = 10000 // Timeout for establishing a connection (10 seconds)
            socketTimeoutMillis = 20000  // Timeout for data transfer after connection is established (20 seconds)
        }
        install(Logging) {
            logger = Logger.SIMPLE
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }

//        install(Auth){
//            bearer {
//                refreshTokens {  }
//            }
//        }
    }


    suspend fun getPosts(): ApiOperation<List<Post>> {
        return safeApiCall {
            client.get("posts/")
                .body<List<RemotePost>>()
                .map {
                    it.toPost()
                }
        }
    }

    suspend fun getPost(id: Int): ApiOperation<Post>{
        return safeApiCall {
            client.get("posts/$id")
                .body<RemotePost>()
                .toPost()
        }
    }

    suspend fun getComments(postId: Int): ApiOperation<List<Comment>>{
        return safeApiCall {
            client.get("posts/$postId/comments/")
                .body<List<RemoteComment>>()
                .map{
                    it.toComment()
                }
        }
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