package com.example.features.categories.service


import com.example.features.categories.repository.CategoryRepository

class CategoryServiceImpl(
    private val repo: CategoryRepository
) : CategoryService {
}