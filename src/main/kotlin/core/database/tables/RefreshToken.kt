package com.example.core.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp

object RefreshToken : Table("refreshToken") {
    val id = char("id", 36)

    val userId = reference(
        name = "user_id",
        refColumn = Admin.id,
        onDelete = ReferenceOption.CASCADE
    )

    val tokenHash = varchar("token_hash", 255)
    val expiresAt = timestamp("expires_at")
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val revoked = bool("revoked").default(false).nullable()

    override val primaryKey = PrimaryKey(id)

}