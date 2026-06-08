package com.example.features.schools.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class SchoolRequest(
    val name: String,
)