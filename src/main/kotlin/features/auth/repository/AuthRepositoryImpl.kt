package com.example.features.auth.repository

import com.example.features.auth.domain.model.AdminModel
import com.example.features.auth.domain.model.RefreshTokenModel
import java.time.Instant
import java.util.UUID

class AuthRepositoryImpl : AuthRepository {


    override suspend fun findAdminByLogin(login: String): AdminModel? {
        TODO("Not yet implemented")
    }

    override suspend fun saveRefreshToken(
        adminId: UUID,
        tokenHash: String,
        expiresAt: Instant
    ): Boolean {
        TODO("Not yet implemented")
    }


    override suspend fun findRefreshTokenByHash(tokenHash: String): RefreshTokenModel? {
        TODO("Not yet implemented")
    }


    override suspend fun revokeRefreshToken(tokenHash: String): Boolean {
        TODO("Not yet implemented")
    }


}