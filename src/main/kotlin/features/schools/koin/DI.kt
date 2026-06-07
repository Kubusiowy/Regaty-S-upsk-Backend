package com.example.features.schools.koin

import com.example.features.schools.repository.SchoolRepository
import com.example.features.schools.repository.SchoolRepositoryImpl
import com.example.features.schools.service.SchoolService
import com.example.features.schools.service.SchoolServiceImpl
import org.koin.dsl.module

val schoolsModule = module {
    single<SchoolRepository> { SchoolRepositoryImpl() }
    single<SchoolService> { SchoolServiceImpl(get()) }
}