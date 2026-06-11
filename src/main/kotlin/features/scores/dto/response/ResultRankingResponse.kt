package com.example.features.scores.dto.response

import com.example.core.util.kSerializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ResultRankingResponse(
    val place: Int,
    @Serializable(with = UUIDSerializer::class)
    val schoolId: UUID,
    val schoolName: String,
    val totalTimeMs: Long

)
