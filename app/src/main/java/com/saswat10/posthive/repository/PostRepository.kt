package com.saswat10.posthive.repository

import com.saswat10.network.KtorClient
import com.saswat10.posthive.di.DataStorage
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val ktorClient: KtorClient,
    private val dataStorage: DataStorage
) {

}