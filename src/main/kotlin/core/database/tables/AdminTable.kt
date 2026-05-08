package com.example.core.database.tables


import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp

object AdminTable : Table("admin") {
    val id = char("id",36)
    val login = varchar("login", 100).uniqueIndex()
    val hashPassword = varchar("hash_password", 100)
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    override val primaryKey = PrimaryKey(id)

}