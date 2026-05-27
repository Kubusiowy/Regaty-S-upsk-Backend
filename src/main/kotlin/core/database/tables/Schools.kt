package com.example.core.database.tables

import org.jetbrains.exposed.sql.Table

object Schools : Table("schools") {
    val id = char("school_id", 36)
    val name = varchar("name", 255)

    override val primaryKey = PrimaryKey(id)

}