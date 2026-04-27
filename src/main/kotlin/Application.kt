package com.example

import com.example.core.database.databaseInit
import com.example.core.plugins.configureFrameworks
import com.example.core.plugins.configureHTTP
import com.example.core.plugins.configureMonitoring
import com.example.core.plugins.configureRouting
import com.example.core.plugins.configureSecurity
import com.example.core.plugins.configureSerialization
import com.example.core.plugins.configureSockets
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    configureFrameworks()
    databaseInit()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureSecurity()
    configureSockets()
    configureRouting()



}
