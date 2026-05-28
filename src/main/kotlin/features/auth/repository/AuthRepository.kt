package com.example.features.auth.repository


import com.example.features.auth.domain.model.AdminModel
import com.example.features.auth.domain.model.RefreshTokenModel
import java.time.Instant
import java.util.UUID

interface AuthRepository {

    suspend fun findAdminByLogin(login: String): AdminModel?

    suspend fun saveRefreshToken(
        id: UUID,
        adminId: UUID,
        tokenHash:String,
        expiresAt: Instant
    ): Boolean


    suspend fun findRefreshTokenByHash(tokenHash: String): RefreshTokenModel?

    suspend fun revokeRefreshToken(tokenHash:String):Boolean
}