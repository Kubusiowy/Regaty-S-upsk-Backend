package com.example.features.auth.domain.DTO.Request

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequest (
    val refreshToken: String
)