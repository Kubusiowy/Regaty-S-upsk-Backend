package com.example.features.schools.repository

import com.example.features.schools.model.SchoolModel
import java.util.UUID

interface SchoolRepository {

    suspend fun getAllSchools(): List<SchoolModel>

    suspend fun getSchoolById(id: UUID): SchoolModel?
    suspend fun createSchool(school: SchoolModel): SchoolModel

    suspend fun updateSchool(school: SchoolModel): SchoolModel

    suspend fun deleteSchool(id: UUID): Int

}