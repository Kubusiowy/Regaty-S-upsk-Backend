package com.example.features.schools.service


import com.example.features.schools.dto.request.SchoolRequest
import com.example.features.schools.dto.request.UpdateSchoolRequest
import com.example.features.schools.dto.response.SchoolResponse
import java.util.UUID

interface SchoolService{
    suspend fun getAllSchools():List<SchoolResponse>

    suspend fun createSchool(school: SchoolRequest):SchoolResponse

    suspend fun updateSchool(id: UUID, school: UpdateSchoolRequest):SchoolResponse

    suspend fun deleteSchool(id: UUID): Int
}