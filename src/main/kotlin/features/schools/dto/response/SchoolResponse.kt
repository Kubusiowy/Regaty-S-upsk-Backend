package com.example.features.schools.dto.response

import com.example.core.util.kSerializer.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class SchoolResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name:String

)