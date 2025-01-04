package com.saswat10.network.models.remote


import com.saswat10.network.convertToRelativeDate
import com.saswat10.network.models.domain.Post
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemotePost(
    val comments: Int,
    val content: String,
    @SerialName("created_at")
    val createdAt: String,
    val id: Int,
    val owner: Owner,
    @SerialName("owner_id")
    val ownerId: Int,
    val published: Boolean,
    val title: String,
    val votes: Int,
    @SerialName("has_voted")
    val hasVoted: Boolean
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

fun RemotePost.toPost(): Post {
    return Post(
        id = id,
        title = title,
        content = content,
        owner = owner.name,
        votes = votes,
        comments = comments,
        createdAt = convertToRelativeDate(createdAt),
        published = published,
        hasVoted = hasVoted
    )
}