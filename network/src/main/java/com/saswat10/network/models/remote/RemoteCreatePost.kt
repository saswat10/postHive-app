package com.saswat10.network.models.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCreatePost(
    val content: String,
    val published: Boolean = true,
    val title: String
)
