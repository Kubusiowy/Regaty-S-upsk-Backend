package com.example.features.schools.repository.mappers

import com.example.core.database.tables.Schools
import com.example.features.schools.model.SchoolModel
import org.jetbrains.exposed.sql.ResultRow
import java.util.UUID

fun ResultRow.toSchoolModel(): SchoolModel  = SchoolModel(
    id = UUID.fromString(this[Schools.id]),
    name = this[Schools.name],
)


