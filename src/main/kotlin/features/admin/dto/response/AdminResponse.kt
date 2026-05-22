package com.example.features.admin.dto.response

import com.example.core.util.kSerializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class AdminResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val login: String,
)