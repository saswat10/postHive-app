package com.saswat10.network.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Registration(
    val name: String,
    val email: String,
    val password: String
)

@Serializable
data class RegistrationResponse(
    val id: Int,
    val email: String,
    val name: String,
    @SerialName("created_at")
    val createdAt: String
)