package com.example.features.auth.domain.model

import java.time.Instant
import java.util.UUID


data class AdminModel(
    val adminId : UUID,
    val login: String,
    val passHash: String,
    val createdAt: Instant,
)
