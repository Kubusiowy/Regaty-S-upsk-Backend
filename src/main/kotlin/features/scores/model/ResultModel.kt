package com.example.features.scores.model

import java.util.UUID

data class ResultModel(
    val id: UUID,
    val schoolId: UUID,
    val categoryId: UUID,
    val timeMs: Long
)