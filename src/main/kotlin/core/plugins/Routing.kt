package com.example.core.plugins

import com.example.features.admin.routes.adminRoutes
import com.example.features.auth.routes.authRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.http.content.defaultResource
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        staticResources("/", "static"){
            default("index.html")
        }
        staticResources("/regaty", "regaty")

        adminRoutes()

        authRoutes()

    }
}
