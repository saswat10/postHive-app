package com.saswat10.posthive.repository

import com.saswat10.network.ApiOperation
import com.saswat10.network.KtorClient
import com.saswat10.network.models.domain.Comment
import com.saswat10.network.models.domain.Post
import com.saswat10.network.models.remote.CreatePostReponse
import com.saswat10.network.models.remote.RemoteCreatePost
import com.saswat10.posthive.di.DataStorage
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val ktorClient: KtorClient,
    private val dataStorage: DataStorage
) {
    suspend fun fetchPosts(): ApiOperation<List<Post>> = ktorClient.getPosts()

    suspend fun fetchPostById(id: Int): ApiOperation<Post> = ktorClient.getPost(id)

    suspend fun fetchComments(postId: Int): ApiOperation<List<Comment>> =
        ktorClient.getComments(postId)

    suspend fun createNewPost(
        body: RemoteCreatePost,
        token: String
    ): ApiOperation<CreatePostReponse> = ktorClient.createPost(body, token)
}