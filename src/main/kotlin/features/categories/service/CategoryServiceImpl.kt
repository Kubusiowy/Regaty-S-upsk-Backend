package com.example.features.categories.service


import com.example.core.plugins.exception.BadRequestException
import com.example.core.plugins.exception.NotFoundException
import com.example.features.categories.dto.request.CreateCategoryRequest
import com.example.features.categories.dto.request.UpdateCategoryRequest
import com.example.features.categories.dto.response.CategoryResponse
import com.example.features.categories.model.CategoryModel
import com.example.features.categories.repository.CategoryRepository
import com.example.features.categories.service.mappers.toResponse
import java.util.UUID

class CategoryServiceImpl(
    private val repo: CategoryRepository
) : CategoryService {

    override suspend fun createCategory(request: CreateCategoryRequest): CategoryResponse {

      val name = nameValidate(request.name)
        val category = CategoryModel(
            id = UUID.randomUUID(),
            name = name,
        )

        val savedCategory = repo.createCategory(category)

        return savedCategory.toResponse()


    }

    override suspend fun getCategories(): List<CategoryResponse>  = repo.getCategories().map { it.toResponse() }


    override suspend fun deleteCategory(id: UUID): Int{
        val deletedRows = repo.deleteCategory(id)

        if(deletedRows == 0) throw NotFoundException("Category with id $id Not Found")

        return deletedRows
    }


    override suspend fun updateCategory(id: UUID, request: UpdateCategoryRequest): CategoryResponse {
        val existingCategory = repo.getCategory(id)
            ?: throw NotFoundException("Category with id $id not found")


        val name = nameValidate(request.name)
       val updatedCategory = CategoryModel(
           id = existingCategory.id,
           name = name,
       )

        val savedCategory = repo.updateCategory(updatedCategory)
        return savedCategory.toResponse()
    }


    private fun nameValidate(name: String): String {
        val trimmedName = name.trim()

        if(trimmedName.isBlank()) throw BadRequestException("Category cannot be blank")
        if (trimmedName.length > 100) throw BadRequestException("Category cannot exceed 100 characters")
        return trimmedName
    }

}