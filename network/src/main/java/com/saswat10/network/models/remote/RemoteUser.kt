package com.saswat10.network.models.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteUser(
    @SerialName("created_at")
    val createdAt: String,
    val email: String,
    val id: Int,
    val name: String
)