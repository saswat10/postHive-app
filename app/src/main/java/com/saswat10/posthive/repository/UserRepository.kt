package com.saswat10.posthive.repository

import com.saswat10.network.ApiOperation
import com.saswat10.network.KtorClient
import com.saswat10.network.models.remote.RemoteToken
import com.saswat10.posthive.di.DataStorage
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val ktorClient: KtorClient,
) {
    suspend fun loginUser(email:String, password: String): ApiOperation<RemoteToken> = ktorClient.loginUser(email, password)
}