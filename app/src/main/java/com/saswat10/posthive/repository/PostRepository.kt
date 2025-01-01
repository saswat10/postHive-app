package com.saswat10.posthive.repository

import com.saswat10.network.ApiOperation
import com.saswat10.network.KtorClient
import com.saswat10.network.models.domain.Post
import com.saswat10.posthive.di.DataStorage
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val ktorClient: KtorClient,
    private val dataStorage: DataStorage
) {
    suspend fun fetchPosts():ApiOperation<List<Post>> = ktorClient.getPosts()
}