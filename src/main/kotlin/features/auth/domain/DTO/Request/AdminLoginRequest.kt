package com.example.features.auth.domain.DTO.Request

import kotlinx.serialization.Serializable


@Serializable
data class AdminLoginRequest(
    val login:String,
    val rawPassword: String
)