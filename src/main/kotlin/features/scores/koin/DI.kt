package com.example.features.scores.koin

import com.example.features.scores.repository.ResultRepository
import com.example.features.scores.repository.ResultRepositoryImpl
import com.example.features.scores.service.ResultService
import com.example.features.scores.service.ResultServiceImpl
import org.koin.dsl.module

val scoresModule = module {
    single<ResultRepository> { ResultRepositoryImpl() }
    single<ResultService> { ResultServiceImpl(get()) }
}