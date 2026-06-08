package com.example.features.categories.service

import com.example.features.categories.dto.request.CreateCategoryRequest
import com.example.features.categories.dto.request.UpdateCategoryRequest
import com.example.features.categories.dto.response.CategoryResponse
import java.util.UUID


interface CategoryService {

    suspend fun createCategory(category: CreateCategoryRequest): CategoryResponse
    suspend fun getCategories(): List<CategoryResponse>

    suspend fun deleteCategory(id: UUID): Int

    suspend fun updateCategory(id: UUID, request: UpdateCategoryRequest): CategoryResponse

}