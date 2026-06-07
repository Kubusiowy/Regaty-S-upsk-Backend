package com.example.features.categories.repository

import com.example.core.database.dbQuery
import com.example.core.database.tables.Categories
import com.example.core.database.tables.Categories.id
import com.example.core.database.tables.Categories.name
import com.example.features.categories.model.CategoryModel
import com.example.features.categories.repository.mappers.toCategoryModel
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.util.UUID

class CategoryRepositoryImpl : CategoryRepository {

    override suspend fun createCategory(category: CategoryModel): CategoryModel{
        dbQuery {
            Categories.insert {
                it[id] = category.id.toString()
                it[name] = category.name
            }
        }

        return category
    }

    override suspend fun getCategory(id: UUID): CategoryModel?{
        return dbQuery {
            Categories
                .selectAll()
                .where{
                    Categories.id eq id.toString()
                }
                .singleOrNull()?.toCategoryModel()
        }
    }

    override suspend fun getCategories(): List<CategoryModel> {
        return dbQuery {
            Categories
                .selectAll()
                .map { it.toCategoryModel() }
        }
    }




    override suspend fun updateCategory(category: CategoryModel): CategoryModel = dbQuery {
        val updatedRows = Categories.update(
            where = {
                Categories.id eq category.id.toString()
            }
        ){
            it[name] = category.name
        }
        category
    }

    override suspend fun deleteCategory(id: UUID) = dbQuery {
        Categories.deleteWhere { Categories.id eq id.toString() }
    }

}