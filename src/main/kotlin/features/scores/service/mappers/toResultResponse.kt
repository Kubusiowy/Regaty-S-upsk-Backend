package com.example.features.scores.service.mappers

import com.example.features.scores.dto.response.ResultResponse
import com.example.features.scores.model.ResultModel
import org.jetbrains.exposed.sql.ResultRow

fun ResultModel.toResultResponse(): ResultResponse = ResultResponse(
    id = this.id,
    schoolId = this.schoolId,
    categoryId = this.categoryId,
    timeMs = this.timeMs,
)