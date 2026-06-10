package com.example.features.scores.dto.request


import kotlinx.serialization.Serializable


@Serializable
data class UpdateResultRequest(
    val timeMs: Long,
)