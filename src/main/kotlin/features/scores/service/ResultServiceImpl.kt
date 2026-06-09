package com.example.features.scores.service

import com.example.features.scores.dto.request.CreateResultRequest
import com.example.features.scores.dto.request.UpdateResultRequest
import com.example.features.scores.dto.response.ResultResponse
import com.example.features.scores.model.ResultModel
import com.example.features.scores.repository.ResultRepository
import com.example.features.scores.service.mappers.toResultResponse
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.NotFoundException
import java.util.UUID

class ResultServiceImpl(
    private val resultRepo: ResultRepository
) : ResultService {


    override suspend fun createResult(request: CreateResultRequest): ResultResponse {

        if(request.timeMs < 0)  {
            throw BadRequestException("time must be > 0")
        }

        val resultModel = ResultModel(
            id = UUID.randomUUID(),
            schoolId = request.schoolId,
            categoryId = request.categoryId,
            timeMs = request.timeMs,
        )
        TODO("create validation to existing result")
        val savedResult = resultRepo.createResult(resultModel)

        return savedResult.toResultResponse()

    }

    override suspend fun updateResult(
        resultId: UUID,
        request: UpdateResultRequest
    ): ResultResponse {
        TODO("Not yet implemented")
    }

    override suspend fun deleteResult(resultId: UUID): Int {
        val deletedRows = resultRepo.delete(resultId)

        if(deletedRows == 0) throw NotFoundException("result with id $resultId not found")

        return deletedRows

    }

    override suspend fun getAllResults(): List<ResultResponse> = resultRepo.getAllResults().map { it.toResultResponse() }


}