package com.example.features.auth.routes

import com.example.features.auth.domain.DTO.Request.AdminLoginRequest
import com.example.features.auth.service.AuthService
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.koin.ktor.ext.inject

fun Route.authRoutes() {

    val authService by inject<AuthService>()

    post("/auth/login") {
        call.receive<AdminLoginRequest>()
        val result = authService.login(call.receive())
    }

}