package com.example.features.auth.repository.mapping

import com.example.core.database.tables.Admin
import com.example.features.auth.domain.model.AdminModel
import org.jetbrains.exposed.sql.ResultRow
import java.util.UUID

fun ResultRow.toAdminModel() = AdminModel(
    adminId = UUID.fromString(this[Admin.id]),
    login = this[Admin.login],
    passHash = this[Admin.hashPassword],
    createdAt = this[Admin.createdAt]
)