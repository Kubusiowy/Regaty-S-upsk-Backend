package com.example.features.auth.service

import com.example.core.plugins.exception.NotFoundException
import com.example.core.plugins.exception.UnauthorizedException
import com.example.core.plugins.security.JwtService
import com.example.core.util.hashing.PassHasher
import com.example.core.util.sha.sha256
import com.example.features.auth.domain.DTO.Request.AdminLoginRequest
import com.example.features.auth.domain.model.LoginResult
import com.example.features.auth.repository.AuthRepository
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID


class AuthServiceImpl(
    private val authRepo: AuthRepository,
    private val passHasher: PassHasher,
    private val jwtService: JwtService
) : AuthService {


    override suspend fun login(
     adminLoginRequest: AdminLoginRequest
    ): LoginResult {

      val admin = authRepo.findAdminByLogin(adminLoginRequest.login) ?: throw UnauthorizedException("Unauthorized")

        val correct = passHasher.verifyPassword(adminLoginRequest.rawPassword, admin.passHash)

        if(!correct) {
            throw UnauthorizedException("Invalid password or login")
        }

        val accessToken = jwtService.generateAccessToken(admin.adminId.toString())
        val refreshToken = jwtService.generateRefreshToken(admin.adminId.toString())
        authRepo.saveRefreshToken(
            id = UUID.randomUUID(),
            adminId = admin.adminId,
            tokenHash = refreshToken.sha256(),
            expiresAt = Instant.now().plus(7, ChronoUnit.DAYS)
        )

        return LoginResult(
            accessToken = accessToken,
            refreshToken = refreshToken,
            adminId = admin.adminId
        )

    }


}