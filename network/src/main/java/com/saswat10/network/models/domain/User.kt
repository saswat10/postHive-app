package com.saswat10.network.models.domain

data class User (
    val id: Int,
    val name: String,
    val email: String,
    val joinedOn: String,
    val posts: List<Post>
)