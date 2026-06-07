package com.example.features.scores.service

import com.example.features.scores.dto.request.CreateResultRequest
import com.example.features.scores.dto.request.UpdateResultRequest
import com.example.features.scores.dto.response.ResultResponse
import com.example.features.scores.repository.ResultRepository
import java.util.UUID

class ResultServiceImpl(
    private val resultRepo: ResultRepository
) : ResultService {
    override suspend fun createResult(request: CreateResultRequest): ResultResponse {
        TODO("Not yet implemented")
    }

    override suspend fun updateResult(
        resultId: UUID,
        request: UpdateResultRequest
    ): ResultResponse {
        TODO("Not yet implemented")
    }

    override suspend fun deleteResult(resultId: UUID) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllResults(): List<ResultResponse> {
        TODO("Not yet implemented")
    }


}