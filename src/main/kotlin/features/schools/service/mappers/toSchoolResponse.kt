package com.example.features.schools.service.mappers

import com.example.features.schools.dto.response.SchoolResponse
import com.example.features.schools.model.SchoolModel

fun SchoolModel.toSchoolResponse() = SchoolResponse(
    id = this.id,
    name = this.name,
)