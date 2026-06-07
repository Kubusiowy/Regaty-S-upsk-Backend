package com.example.features.scores.dto.request

import com.example.core.util.kSerializer.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UpdateResultRequest(
    @Serializable(with = UUIDSerializer::class)
    val schoolId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val categoryId: UUID,
    val timeMs: Long,
)