package com.example.features.categories.service.mappers

import com.example.features.categories.dto.response.CategoryResponse
import com.example.features.categories.model.CategoryModel

fun CategoryModel.toResponse() = CategoryResponse(
    id = this.id,
    name = this.name
)