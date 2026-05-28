package com.example.features.auth.repository

import com.example.core.database.dbQuery
import com.example.core.database.tables.Admin
import com.example.core.database.tables.RefreshToken
import com.example.features.auth.domain.model.AdminModel
import com.example.features.auth.domain.model.RefreshTokenModel
import com.example.features.auth.repository.mapping.toAdminModel
import com.example.features.auth.repository.mapping.toRefreshTokenModel
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.time.Instant
import java.util.UUID

class AuthRepositoryImpl : AuthRepository {


    override suspend fun findAdminByLogin(login: String): AdminModel? {
        return dbQuery {
            Admin
                .selectAll()
                .where{Admin.login eq login }
                .singleOrNull()
                ?.toAdminModel()
        }

    }

    override suspend fun saveRefreshToken(
        id: UUID,
        adminId: UUID,
        tokenHash: String,
        expiresAt: Instant
    ): Boolean {
       return dbQuery {
            RefreshToken.insert {
                it[this.id] = id.toString()
                it[userId] = adminId.toString()
                it[this.tokenHash] = tokenHash
                it[this.expiresAt] = expiresAt
            }.insertedCount > 0
        }
    }


    override suspend fun findRefreshTokenByHash(tokenHash: String): RefreshTokenModel? {
        return dbQuery {
            RefreshToken
                .selectAll()
                .where { RefreshToken.tokenHash eq tokenHash }
                .singleOrNull()
                ?.toRefreshTokenModel()
        }
    }


    override suspend fun revokeRefreshToken(tokenHash: String): Boolean {
        return dbQuery {
            RefreshToken.update(
                where = { RefreshToken.tokenHash eq tokenHash }
            ) {
                it[revoked] = true
            } > 0
        }
    }


}