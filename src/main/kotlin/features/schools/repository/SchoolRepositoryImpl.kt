package com.example.features.schools.repository

import com.example.core.database.dbQuery
import com.example.core.database.tables.Schools
import com.example.features.schools.model.SchoolModel
import com.example.features.schools.repository.mappers.toSchoolModel
import io.ktor.server.plugins.NotFoundException
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.util.UUID

class SchoolRepositoryImpl : SchoolRepository {

    override suspend fun getAllSchools(): List<SchoolModel>  = dbQuery {
        Schools
            .selectAll()
            .map { it.toSchoolModel() }
    }

    override suspend fun getSchoolById(id: UUID): SchoolModel? = dbQuery {
        Schools
            .selectAll()
            .where(Schools.id eq id.toString())
            .singleOrNull()?.toSchoolModel()
    }

    override suspend fun createSchool(school: SchoolModel): SchoolModel = dbQuery {
        Schools.insert {
            it[id] = school.id.toString()
            it[name] = school.name
        }
        school
    }



    override suspend fun updateSchool(school: SchoolModel): SchoolModel = dbQuery {
        val updatedRows = Schools.update(
            where ={ Schools.id eq school.id.toString() }) {
            it[name] = school.name
        }

        if (updatedRows == 0) throw NotFoundException("School with id ${school.id} not found")
        school
    }

    override suspend fun deleteSchool(id: UUID): Int = dbQuery {
        Schools.deleteWhere { Schools.id eq id.toString() }
    }
}