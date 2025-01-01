package com.saswat10.network.models.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteComment(
    val content: String,
    @SerialName("created_at")
    val createdAt: String,
    val id: Int,
    @SerialName("post_id")
    val postId: Int,
    val user: User,
    @SerialName("user_id")
    val userId: Int
) {
    @Serializable
    data class User(
        @SerialName("created_at")
        val createdAt: String,
        val email: String,
        val id: Int,
        val name: String
    )
}