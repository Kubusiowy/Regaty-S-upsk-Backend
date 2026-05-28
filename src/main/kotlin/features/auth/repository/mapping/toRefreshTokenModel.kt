package com.example.features.auth.repository.mapping

import com.example.core.database.tables.Admin
import com.example.core.database.tables.RefreshToken
import com.example.features.auth.domain.model.RefreshTokenModel
import org.jetbrains.exposed.sql.ResultRow
import java.util.UUID

fun ResultRow.toRefreshTokenModel(): RefreshTokenModel = RefreshTokenModel(
    id = UUID.fromString(this[RefreshToken.id]),
    userId = UUID.fromString(this[RefreshToken.userId]),
    tokenHash = this[RefreshToken.tokenHash],
    expiresAt = this[RefreshToken.expiresAt],
    createdAt = this[RefreshToken.createdAt],
    revoked = this[RefreshToken.revoked]
)