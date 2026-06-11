package com.example.core.plugins.security.koin

import com.example.core.plugins.security.service.JwtService
import com.example.core.plugins.security.service.JwtServiceImpl
import org.koin.dsl.module

val securityModule = module{
    single<JwtService> { JwtServiceImpl(get()) }
}