package com.example.features.scores.dto.response

import com.example.core.util.kSerializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ResultResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    val schoolId:UUID,
    @Serializable(with = UUIDSerializer::class)
    val categoryId:UUID,
    val timeMs: Long,

)
