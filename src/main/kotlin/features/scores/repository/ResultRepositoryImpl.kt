package com.example.features.scores.repository

import com.example.core.database.dbQuery
import com.example.core.database.tables.Results
import com.example.features.scores.dto.response.ResultResponse
import com.example.features.scores.model.ResultModel
import com.example.features.scores.repository.mapping.toResultModel
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.util.UUID

class ResultRepositoryImpl : ResultRepository {

    override suspend fun createResult(resultModel: ResultModel): ResultModel {
        dbQuery {
            Results.insert {
                it[id] = resultModel.id.toString()
                it[schoolId] = resultModel.schoolId.toString()
                it[categoryId] = resultModel.categoryId.toString()
                it[timeMs] = resultModel.timeMs
            }
        }
        return resultModel
    }

    override suspend fun updateResult(
        resultId: UUID,
        timeMs: Long
    ): ResultModel {
        dbQuery {
            TODO()
        }
    }

    override suspend fun getAllResults(): List<ResultModel> {
       return dbQuery {
            Results
                .selectAll()
                .map {
                    it.toResultModel()
                }
        }
    }

    override suspend fun delete(id: UUID) {
        TODO("Not yet implemented")
    }

}