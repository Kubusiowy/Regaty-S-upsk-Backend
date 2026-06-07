package com.example.features.scores.repository

import com.example.features.scores.dto.response.ResultResponse
import com.example.features.scores.model.ResultModel
import java.util.UUID

interface ResultRepository {

    suspend fun createResult(resultModel: ResultModel): ResultModel
    suspend fun updateResult(
        resultId: UUID,
        timeMs: Long,
    ): ResultModel

    suspend fun getAllResults(): List<ResultModel>

    suspend fun delete(id: UUID)
}