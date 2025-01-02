package com.saswat10.network.models.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePostReponse(
    val content: String,
    @SerialName("created_at")
    val createdAt: String,
    val id: Int,
    val owner: Owner,
    @SerialName("owner_id")
    val ownerId: Int,
    val published: Boolean,
    val title: String
) {
    @Serializable
    data class Owner(
        @SerialName("created_at")
        val createdAt: String,
        val email: String,
        val id: Int,
        val name: String
    )
}