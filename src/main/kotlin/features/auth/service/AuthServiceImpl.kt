package com.example.features.auth.service

import com.example.core.plugins.security.JwtService
import com.example.core.util.hashing.PassHasher
import com.example.features.auth.repository.AuthRepository

class AuthServiceImpl(
    private val authRepo: AuthRepository,
    private val passHasher: PassHasher,
    private val jwtService: JwtService
) : AuthService {
}