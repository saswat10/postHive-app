package com.saswat10.posthive.repository

import com.saswat10.network.ApiOperation
import com.saswat10.network.KtorClient
import com.saswat10.network.models.domain.Comment
import com.saswat10.network.models.domain.Post
import com.saswat10.network.models.remote.CreatePostReponse
import com.saswat10.network.models.remote.RCommentCreate
import com.saswat10.network.models.remote.RemoteCreatePost
import com.saswat10.posthive.di.DataStorage
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val ktorClient: KtorClient,
    private val dataStorage: DataStorage
) {
    suspend fun fetchPosts(token: String): ApiOperation<List<Post>> = ktorClient.getPosts(token)

    suspend fun fetchPostById(id: Int, token: String): ApiOperation<Post> = ktorClient.getPost(id, token)

    suspend fun fetchComments(postId: Int): ApiOperation<List<Comment>> =
        ktorClient.getComments(postId)

    suspend fun createNewPost(
        body: RemoteCreatePost,
        token: String
    ): ApiOperation<CreatePostReponse> = ktorClient.createPost(body, token)

    suspend fun updatePost(
        body: RemoteCreatePost,
        token: String,
        id: Int
    ): ApiOperation<CreatePostReponse> = ktorClient.updatePost(body, token, id)

    suspend fun deletePost(
        id: Int,
        token: String
    ) = ktorClient.deletePost(id, token)

    suspend fun toggleVote(
        postId: Int,
        token: String
    ) = ktorClient.toggleVote(postId, token)

    suspend fun addComment(
        postId: Int,
        token: String,
        content: RCommentCreate
    ) = ktorClient.createComment(content, postId, token)

    suspend fun updateComment(
        commentId: Int,
        token: String,
        content: RCommentCreate
    ) = ktorClient.updateComment(content, commentId, token)

    suspend fun deleteComment(
        commentId: Int,
        token: String
    ) = ktorClient.deleteComment(commentId, token)

}