package com.example.features.categories.service

import com.example.features.categories.repository.CategoryReposiotry

class CategoryServiceImpl(
    private val repo: CategoryReposiotry
) : CategoryService {
}