package com.example.core.database

import com.example.core.database.tables.Admin
import com.example.core.util.hashing.PassHasher
import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.util.UUID

suspend fun seedAdmin(
    config: ApplicationConfig,
    passHasher: PassHasher
){

    val login = config.property("admin.login").getString()
    val rawPass = config.property("admin.rawPass").getString()

    dbQuery {
        val exists = Admin
            .selectAll()
            .where { Admin.login eq login }
            .limit(1)
            .count() > 0

        if(!exists){
            Admin.insert {
                it[Admin.id] = UUID.randomUUID().toString()
                it[Admin.login] = login
                it[Admin.hashPassword] = passHasher.hashPassword(rawPass)
            }
        }
    }

}