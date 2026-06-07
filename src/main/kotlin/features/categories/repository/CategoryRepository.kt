package com.example.features.categories.repository


import com.example.features.categories.model.CategoryModel
import java.util.UUID

interface CategoryRepository {

   suspend fun createCategory(category: CategoryModel): CategoryModel

   suspend fun getCategory(id: UUID): CategoryModel?

   suspend fun getCategories(): List<CategoryModel>




    suspend fun updateCategory(category: CategoryModel): CategoryModel

    suspend fun deleteCategory(id: UUID): Int
}