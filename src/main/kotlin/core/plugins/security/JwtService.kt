package com.example.core.plugins.security

import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.interfaces.DecodedJWT

interface JwtService {
    fun generateRefreshToken(userId: String): String
    fun verifyRefreshToken(token: String): DecodedJWT
    fun generateAccessToken(userId: String):String
    val verifier: JWTVerifier
}

