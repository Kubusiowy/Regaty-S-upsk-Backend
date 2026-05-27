package com.example.core.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Results : Table("results") {
    val id = char("id", 36)

    val schoolId = reference(
        name = "school_id",
        refColumn = Schools.id,
        onDelete = ReferenceOption.CASCADE,
    )

    val categoryId = reference(
        name = "category_id",
        refColumn = Categories.id,
        onDelete = ReferenceOption.CASCADE,
    ).nullable()


    override val primaryKey = PrimaryKey(id)

    init{
        uniqueIndex(schoolId, categoryId)
    }

}