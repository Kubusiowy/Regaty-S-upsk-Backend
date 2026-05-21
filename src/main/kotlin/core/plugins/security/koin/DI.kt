package com.example.core.plugins.security.koin

import com.example.core.plugins.security.JwtService
import com.example.core.plugins.security.JwtServiceImpl
import org.koin.dsl.module

val securityModule = module{
    single<JwtService> { JwtServiceImpl(get()) }
}