package com.saswat10.network.models.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RCommentCreate(
    val content: String
)