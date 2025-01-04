package com.saswat10.network.models.remote


import com.saswat10.network.convertToRelativeDate
import com.saswat10.network.models.domain.Post
import com.saswat10.network.models.domain.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteUserWithPosts(
    @SerialName("created_at")
    val createdAt: String,
    val email: String,
    val id: Int,
    val name: String,
    val posts: List<RemoteUserPost>
)

@Serializable
data class RemoteUserPost(
    val comments: Int,
    val content: String,
    @SerialName("created_at")
    val createdAt: String,
    val id: Int,
    @SerialName("owner_id")
    val ownerId: Int,
    val published: Boolean,
    val title: String,
    val votes: Int
)

fun RemoteUserPost.toPost() :Post{
    return Post(
        id = id,
        createdAt = convertToRelativeDate(createdAt),
        content =  content,
        ownerId =  ownerId,
        title = title,
        published = published,
        votes = votes,
        comments = comments,
        hasVoted = false,
        owner = ""
    )
}

fun RemoteUserWithPosts.toUser(): User {
    return User(
        id = id,
        name= name,
        email = email,
        joinedOn = convertToRelativeDate(createdAt),
        posts = posts.map { it.toPost() }
    )
}