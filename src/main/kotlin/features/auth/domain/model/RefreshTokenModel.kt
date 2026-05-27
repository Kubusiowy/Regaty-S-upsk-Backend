package com.example.features.auth.domain.model

import java.time.Instant
import java.util.UUID


data class RefreshTokenModel(
    val id : UUID,
    val userId : UUID,
    val tokenHash: String,
    val expiresAt: Instant,
    val createdAt : Instant,
    val revoked : Boolean?,
)
