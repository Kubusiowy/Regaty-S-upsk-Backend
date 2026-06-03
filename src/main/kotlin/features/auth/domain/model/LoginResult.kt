package com.example.features.auth.domain.model

import java.util.UUID

data class LoginResult(
    val accessToken:String,
    val refreshToken:String,
    val adminId: UUID
)
