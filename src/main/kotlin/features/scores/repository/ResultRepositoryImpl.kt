package com.example.features.scores.repository

import com.example.core.database.dbQuery
import com.example.core.database.tables.Results
import com.example.core.database.tables.Schools
import com.example.core.plugins.exception.BadRequestException
import com.example.features.scores.dto.request.UpdateResultRequest
import com.example.features.scores.dto.response.ResultRankingResponse
import com.example.features.scores.dto.response.ResultResponse
import com.example.features.scores.model.ResultModel
import com.example.features.scores.repository.mapping.toResultModel
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.javatime.time
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.sum
import org.jetbrains.exposed.sql.update
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


    override suspend fun getBySchoolAndCategory(schoolId: UUID, categoryId: UUID): ResultModel? = dbQuery {
        Results
            .selectAll()
            .where{
                (Results.schoolId eq schoolId.toString()) and
                        (Results.categoryId eq categoryId.toString())
            }
            .singleOrNull()?.toResultModel()
    }
    override suspend fun updateResult(
        resultModel: ResultModel,
    ): ResultModel = dbQuery {
        val updatedRows = Results.update(
            where = { Results.id eq resultModel.id.toString()}
        ) {
            it[timeMs] = resultModel.timeMs
        }
        if(updatedRows == 0) throw BadRequestException("school with id ${resultModel.id} was not found")
        resultModel
    }



    override suspend fun getAllResults(): List<ResultModel> = dbQuery {
            Results
                .selectAll()
                .map {it.toResultModel()}
        }


    override suspend fun getResult(id: UUID): ResultModel? =dbQuery {
        Results
            .selectAll()
            .where{ Results.id eq id.toString() }
            .singleOrNull()?.toResultModel()
    }

    override suspend fun delete(id: UUID): Int = dbQuery {
        Results.deleteWhere {Results.id eq id.toString()}
    }

    override suspend fun getRanking(): List<ResultRankingResponse> = dbQuery {
        val totalTime = Results.timeMs.sum()

        Results
            .innerJoin(Schools)
            .select(
                Results.schoolId,
                Schools.name,
                totalTime
            )
            .groupBy(
                Results.schoolId,
                Schools.name
            )
            .orderBy(totalTime, SortOrder.ASC)
            .mapIndexed { index, row ->
                ResultRankingResponse(
                    place = index + 1,
                    schoolId = UUID.fromString(row[Results.schoolId]),
                    schoolName = row[Schools.name],
                    totalTimeMs = row[totalTime] ?: 0L
                )
            }
    }

}