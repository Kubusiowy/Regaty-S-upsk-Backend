package com.example.core.plugins

import com.example.features.auth.routes.authRoutes
import com.example.features.auth.service.AuthService
import com.example.features.scores.routes.scoreAdminRoutes
import com.example.features.scores.service.ResultService
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.http.content.staticResources
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val authService by inject<AuthService>()
    val resultService by inject<ResultService>()
    routing {
        staticResources("/", "static"){
            default("index.html")
        }

        authenticate("auth-jwt") {
            scoreAdminRoutes(resultService)
        }

        authRoutes(authService)

    }
}
