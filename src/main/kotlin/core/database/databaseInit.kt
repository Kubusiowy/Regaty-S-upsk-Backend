package com.example.core.database

import io.ktor.server.application.Application
import org.koin.ktor.ext.inject

fun Application.databaseInit() {
    val databaseFactory by inject<DatabaseFactory>()
    databaseFactory.init()

}