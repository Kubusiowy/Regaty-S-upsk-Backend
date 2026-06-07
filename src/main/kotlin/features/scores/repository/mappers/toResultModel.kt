package com.example.features.scores.repository.mapping

import com.example.core.database.tables.Results
import com.example.features.scores.model.ResultModel
import org.jetbrains.exposed.sql.ResultRow
import java.util.UUID

fun ResultRow.toResultModel(): ResultModel =
    ResultModel(
        id = UUID.fromString(this[Results.id]),
        schoolId = UUID.fromString(this[Results.schoolId]),
        categoryId = UUID.fromString(this[Results.categoryId]),
        timeMs = this[Results.timeMs]
    )