package com.example.features.admin.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class AdminLoginResponse (
    val accessToken: String,
    val refreshToken: String,
    val admin:AdminResponse
)