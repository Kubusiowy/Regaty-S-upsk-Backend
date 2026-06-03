package com.example.features.auth.domain.DTO.Response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken:String,
    val refreshToken:String
)
