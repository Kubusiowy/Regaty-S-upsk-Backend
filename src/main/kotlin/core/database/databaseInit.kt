package com.example.core.database

import com.example.core.util.hashing.PassHasher
import io.ktor.server.application.Application
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.inject

fun Application.databaseInit() {
    val databaseFactory by inject<DatabaseFactory>()
    val passHasher by inject<PassHasher>()

    databaseFactory.init()

    runBlocking {
        seedAdmin(environment.config, passHasher)
    }

}