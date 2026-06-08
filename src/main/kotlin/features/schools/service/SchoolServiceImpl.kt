package com.example.features.schools.service

import com.example.features.schools.dto.request.SchoolRequest
import com.example.features.schools.dto.request.UpdateSchoolRequest
import com.example.features.schools.dto.response.SchoolResponse
import com.example.features.schools.model.SchoolModel
import com.example.features.schools.repository.SchoolRepository
import com.example.features.schools.service.mappers.toSchoolResponse
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.NotFoundException
import java.util.UUID

class SchoolServiceImpl(
    private val schoolRepo: SchoolRepository
) : SchoolService {
    override suspend fun getAllSchools(): List<SchoolResponse> = schoolRepo.getAllSchools().map { it.toSchoolResponse() }


    override suspend fun createSchool(school: SchoolRequest): SchoolResponse {
        val name = nameValidate(school.name)

        val schoolModel = SchoolModel(
            id = UUID.randomUUID(),
            name = name,
        )

        val savedSchool = schoolRepo.createSchool(schoolModel)

        return savedSchool.toSchoolResponse()

    }

    override suspend fun updateSchool(
        id: UUID,
        school: UpdateSchoolRequest
    ): SchoolResponse {

        val existingSchool = schoolRepo.getSchoolById(id) ?: throw NotFoundException("School with id $id not found")

        val name = nameValidate(school.name)

       val schoolToUpdate = SchoolModel(
           id = existingSchool.id,
           name = name,
       )

        val savedSchool = schoolRepo.updateSchool(schoolToUpdate)
        return savedSchool.toSchoolResponse()

    }

    override suspend fun deleteSchool(id: UUID): Int {
       val deletedRows = schoolRepo.deleteSchool(id)

        if(deletedRows == 0) throw NotFoundException("School with id $id not found")
        return deletedRows
    }

    private fun nameValidate(name: String): String{
        val nameTrimmed = name.trim()

        if(nameTrimmed.isBlank()) throw BadRequestException("School cannot be blank")
        if(nameTrimmed.length > 100) throw BadRequestException("School cannot exceed 100 characters")
        return nameTrimmed
    }
}

