package com.example.features.auth.di

import com.example.features.auth.repository.AuthRepository
import com.example.features.auth.repository.AuthRepositoryImpl
import com.example.features.auth.service.AuthService
import com.example.features.auth.service.AuthServiceImpl
import org.koin.dsl.module

val authModule = module{

    single<AuthRepository> { AuthRepositoryImpl() }
    single<AuthService> { AuthServiceImpl(
        authRepo = get(),
        passHasher = get(),
        jwtService = get()
    ) }
}