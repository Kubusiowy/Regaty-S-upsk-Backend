package com.example.features.categories.koin

import com.example.features.categories.repository.CategoryReposiotry
import com.example.features.categories.repository.CategoryRepositoryImpl
import com.example.features.categories.service.CategoryService
import com.example.features.categories.service.CategoryServiceImpl

import org.koin.dsl.module

val categoriesModule = module {
    single<CategoryReposiotry> { CategoryRepositoryImpl() }

    single<CategoryService> { CategoryServiceImpl(get()) }


}