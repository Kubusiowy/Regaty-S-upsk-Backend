package com.example.features.scores.repository

import com.example.features.scores.dto.response.ResultResponse
import com.example.features.scores.model.ResultModel
import java.util.UUID

class ResultRepositoryImpl : ResultRepository {

    override suspend fun createResult(resultModel: ResultModel): ResultModel {
        TODO("Not yet implemented")
    }

    override suspend fun updateResult(
        resultId: UUID,
        timeMs: Long
    ): ResultModel {
        TODO("Not yet implemented")
    }

    override suspend fun getAllResults(): List<ResultResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: UUID) {
        TODO("Not yet implemented")
    }

}