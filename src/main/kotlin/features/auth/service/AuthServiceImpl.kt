package com.example.features.auth.service

import com.example.core.plugins.exception.NotFoundException
import com.example.core.plugins.exception.UnauthorizedException
import com.example.core.plugins.security.JwtService
import com.example.core.util.hashing.PassHasher
import com.example.features.auth.domain.DTO.Request.AdminLoginRequest
import com.example.features.auth.domain.DTO.Response.LoginResponse
import com.example.features.auth.domain.model.LoginResult
import com.example.features.auth.repository.AuthRepository

class AuthServiceImpl(
    private val authRepo: AuthRepository,
    private val passHasher: PassHasher,
    private val jwtService: JwtService
) : AuthService {


    override suspend fun login(
     adminLoginRequest: AdminLoginRequest
    ): LoginResult {

      val admin = authRepo.findAdminByLogin(adminLoginRequest.login) ?: throw NotFoundException("Admin not Found")

        val correct = passHasher.verifyPassword(adminLoginRequest.rawPassword, admin.passHash)

        if(!correct) {
            throw UnauthorizedException("Invalid password or login")
        }

        val accessToken = jwtService.generateAccessToken(admin.adminId.toString())
        val refreshToken = jwtService.generateRefreshToken(admin.adminId.toString())

        return LoginResult(
            accessToken = accessToken,
            refreshToken = refreshToken,
            adminId = admin.adminId
        )

    }


}