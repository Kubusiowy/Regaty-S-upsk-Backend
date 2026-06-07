package com.example.features.scores.service

import com.example.features.scores.dto.request.CreateResultRequest
import com.example.features.scores.dto.response.ResultResponse
import com.example.features.scores.repository.ResultRepository

class ResultServiceImpl(
    private val resultRepo: ResultRepository
) : ResultService {
    override suspend fun createResult(request: CreateResultRequest): ResultResponse {
        TODO("Not yet implemented")
    }


}