package com.example.features.categories.dto.request


import kotlinx.serialization.Serializable


@Serializable
data class UpdateCategoryRequest(
    val name: String,
)
