package com.example.features.scores.service

import com.example.features.scores.dto.request.CreateResultRequest
import com.example.features.scores.dto.request.UpdateResultRequest
import com.example.features.scores.dto.response.ResultResponse
import java.util.UUID

interface ResultService {

    suspend fun createResult(request: CreateResultRequest): ResultResponse
    suspend fun updateResult(resultId: UUID, request: UpdateResultRequest): ResultResponse
    suspend fun deleteResult(resultId: UUID): Int
    suspend fun getAllResults(): List<ResultResponse>
}