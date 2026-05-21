package com.example.core.plugins.security

import com.auth0.jwt.JWTVerifier

interface JwtService {
    fun generateRefreshToken(userId: String): String
    fun generateAccessToken(userId: String):String
    val verifier: JWTVerifier
}

