package com.example.core.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respondText


fun Application.configureStatusPages() {
    install(StatusPages) {

    }
}