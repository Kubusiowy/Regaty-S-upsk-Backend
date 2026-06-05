package com.example.features.auth.domain.DTO.Response

import kotlinx.serialization.Serializable

@Serializable
data class RefreshResponse(
    val accessToken: String
)