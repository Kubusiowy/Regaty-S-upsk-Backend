package com.example.features.scores.repository

import com.example.features.scores.dto.request.UpdateResultRequest
import com.example.features.scores.dto.response.ResultRankingResponse
import com.example.features.scores.dto.response.ResultResponse
import com.example.features.scores.model.ResultModel
import java.util.UUID

interface ResultRepository {

    suspend fun createResult(resultModel: ResultModel): ResultModel

    suspend fun updateResult(
        resultModel: ResultModel,
    ): ResultModel

    suspend fun getBySchoolAndCategory(schoolId:UUID, categoryId:UUID): ResultModel?

    suspend fun getAllResults(): List<ResultModel>
    suspend fun getResult(id: UUID): ResultModel?
    suspend fun delete(id: UUID):Int

    suspend fun getRanking(): List<ResultRankingResponse>
}