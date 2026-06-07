package com.example.features.categories.repository.mappers

import com.example.core.database.tables.Categories
import com.example.features.categories.model.CategoryModel
import org.jetbrains.exposed.sql.ResultRow
import java.util.UUID

fun ResultRow.toCategoryModel(): CategoryModel =
    CategoryModel(
        id = UUID.fromString(this[Categories.id]),
        name = this[Categories.name],
    )