package com.example.core.database.tables

import org.jetbrains.exposed.sql.Table

object Schools : Table("schools") {
    val id = uuid("school_id")
    val name = varchar("name", 255)

    override val primaryKey = PrimaryKey(id)

}