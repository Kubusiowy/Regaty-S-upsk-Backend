package com.example.core.plugins.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.config.ApplicationConfig
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date

class JwtServiceImpl(
    config: ApplicationConfig
) : JwtService {

    val jwtAudience = config.property("jwt.audience").getString()
    val jwtDomain = config.property("jwt.domain").getString()
    val jwtSecret = config.property("jwt.secret").getString()

    private val algorithm = Algorithm.HMAC256(jwtSecret)

    override fun generateRefreshToken(userId: String): String {
        return JWT.create()
            .withIssuer(jwtDomain)
            .withAudience(jwtAudience)
            .withClaim("userId",userId)
            .withClaim("type", "refresh")
            .withExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS))
            .sign(algorithm)
    }

    override fun generateAccessToken(userId: String): String {
        return JWT.create()
            .withIssuer(jwtDomain)
            .withAudience(jwtAudience)
            .withClaim("userId", userId)
            .withClaim("type", "access")
            .withExpiresAt(Date(System.currentTimeMillis() + 15 * 60 * 1000))
            .sign(algorithm)
    }

    override val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(jwtDomain)
        .withAudience(jwtAudience)
        .build()
}