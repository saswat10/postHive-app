package com.saswat10.network.models.domain

data class Post (
    val id: Int,
    val title: String,
    val content: String,
    val owner: String,
    val votes: Int,
    val comments: Int,
    val createdAt: String,
    val published: Boolean,
    val hasVoted: Boolean
)
