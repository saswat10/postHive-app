package com.saswat10.network

import com.saswat10.network.models.domain.Comment
import com.saswat10.network.models.domain.Post
import com.saswat10.network.models.domain.User
import com.saswat10.network.models.remote.CreatePostReponse
import com.saswat10.network.models.remote.RCommentCreate
import com.saswat10.network.models.remote.Registration
import com.saswat10.network.models.remote.RegistrationResponse
import com.saswat10.network.models.remote.RemoteComment
import com.saswat10.network.models.remote.RemoteCreatePost
import com.saswat10.network.models.remote.RemotePost
import com.saswat10.network.models.remote.RemoteRegistration
import com.saswat10.network.models.remote.RemoteToken
import com.saswat10.network.models.remote.RemoteUser
import com.saswat10.network.models.remote.RemoteUserWithPosts
import com.saswat10.network.models.remote.toComment
import com.saswat10.network.models.remote.toPost
import com.saswat10.network.models.remote.toUser
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.delete
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.InternalAPI
import kotlinx.serialization.json.Json

class KtorClient {
    private val client = HttpClient(OkHttp) {
        defaultRequest { url("https://posthive-api.onrender.com/") }
        install(HttpTimeout) {
            requestTimeoutMillis = 60000 // Total timeout for the request (60 seconds)
            connectTimeoutMillis = 60000 // Timeout for establishing a connection (60 seconds)
            socketTimeoutMillis =
                60000  // Timeout for data transfer after connection is established (60 seconds)
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
        install(HttpRequestRetry) {
            maxRetries = 5 // Number of retry attempts
            retryIf { request, response ->
                !response.status.isSuccess()
            }
            retryIf { request, response ->
                // Retry if response status is in the 5xx range
                response.status.value in 500..599
            }
            delayMillis { retry ->
                retry * 1000L // Exponential backoff: 1s, 2s, 3s...
            }
        }
    }


    suspend fun getPosts(token: String): ApiOperation<List<Post>> {
        return safeApiCall {
            client.get("posts/") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }
                .body<List<RemotePost>>()
                .map {
                    it.toPost()
                }
        }
    }

    private var postCache = mutableMapOf<Int, Post>()
    suspend fun getPost(id: Int, token: String): ApiOperation<Post> {
        postCache[id]?.let {
            return ApiOperation.Success(it)
        }

        val post = client.get("posts/$id") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }
            .body<RemotePost>()
            .toPost()
        postCache[id] = post
        return safeApiCall { post }

    }

    suspend fun createPost(body: RemoteCreatePost, token: String): ApiOperation<CreatePostReponse> {
        return safeApiCall {
            client.post("posts/") {
                contentType(ContentType.Application.Json)
                setBody(body)
                header(HttpHeaders.Authorization, "Bearer $token")
            }.body<CreatePostReponse>()
        }
    }

    suspend fun updatePost(
        body: RemoteCreatePost,
        token: String,
        id: Int
    ): ApiOperation<CreatePostReponse> {
        return safeApiCall {
            client.put("posts/$id") {
                contentType(ContentType.Application.Json)
                setBody(body)
                header(HttpHeaders.Authorization, "Bearer $token")
            }.body<CreatePostReponse>()
        }
    }

    suspend fun deletePost(id: Int, token: String): ApiOperation<HttpResponse> {
        return safeApiCall {
            client.delete("posts/$id") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }.body()
        }
    }

    private var CommmentsCache = mutableMapOf<Int, List<Comment>>()
    suspend fun getComments(postId: Int): ApiOperation<List<Comment>> {
        CommmentsCache[postId]?.let {
            return ApiOperation.Success(it)
        }

        val comments = client.get("posts/$postId/comments/")
                .body<List<RemoteComment>>()
                .map {
                    it.toComment()
                }
        CommmentsCache[postId] = comments
        return safeApiCall {
            comments
        }
    }

    suspend fun createComment(content: RCommentCreate, postId: Int, token: String): ApiOperation<Comment> {
        return safeApiCall {
            client.post("posts/$postId/comments/") {
                contentType(ContentType.Application.Json)
                setBody(content)
                header(HttpHeaders.Authorization, "Bearer $token")
            }.body<RemoteComment>().toComment()
        }
    }

    suspend fun updateComment(
        content: RCommentCreate,
        commentId: Int,
        token: String
    ): ApiOperation<Comment> {
        return safeApiCall {
            client.put("comments/$commentId") {
                contentType(ContentType.Application.Json)
                setBody(content)
                header(HttpHeaders.Authorization, "Bearer $token")
            }.body<RemoteComment>().toComment()
        }
    }

    suspend fun deleteComment(commentId: Int, token: String): ApiOperation<HttpResponse> {
        return safeApiCall {
            client.delete("comments/$commentId") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }.body()
        }
    }


    suspend fun registerUser(body: RemoteRegistration): ApiOperation<RemoteUser> {
        return safeApiCall {
            client.post("users/") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }.body<RemoteUser>()
        }
    }

    suspend fun loginUser(username: String, password: String): ApiOperation<RemoteToken> {
        return safeApiCall {
            client.post("/login") {
                contentType(ContentType.Application.FormUrlEncoded)
                setBody(FormDataContent(
                    Parameters.build {
                        append("username", username)
                        append("password", password)
                    }
                ))
            }.body<RemoteToken>()
        }
    }


    suspend fun getMe(token: String): ApiOperation<User> {
        return safeApiCall {
            client.get("users/user/me") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }.body<RemoteUserWithPosts>()
                .toUser()
        }
    }

    suspend fun toggleVote(postId: Int, token: String): ApiOperation<HttpResponse> {
        return safeApiCall {
            client.post("vote/$postId") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }.body()
        }
    }

    suspend fun checkVote() {
        // todo
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