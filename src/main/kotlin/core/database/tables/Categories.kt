package com.example.core.database.tables

import org.jetbrains.exposed.sql.Table

object Categories : Table("categories") {

    val id = char("id", 36)
    val name = varchar("name", 100)

    override val primaryKey = PrimaryKey(id)
}