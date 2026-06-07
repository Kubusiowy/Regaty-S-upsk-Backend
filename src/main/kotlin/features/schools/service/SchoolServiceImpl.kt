package com.example.features.schools.service

import com.example.features.schools.repository.SchoolRepository

class SchoolServiceImpl(
    private val schoolRepo: SchoolRepository
) : SchoolService {
}