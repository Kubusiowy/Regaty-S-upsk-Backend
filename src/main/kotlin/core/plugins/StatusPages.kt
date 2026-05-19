package com.example.core.plugins

import com.example.core.plugins.exception.ApiException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.response.respondText


fun Application.configureStatusPages() {
    install(StatusPages) {

        exception<ApiException>{call,cause ->
            call.respond(
                status = cause.status,
                message = mapOf(
                    "error" to (cause.message ?: "Unknown error")
                )
            )
        }

        status(HttpStatusCode.NotFound){ call, status ->
            call.respond(
                status,
                mapOf("error" to "Route Not Found")
            )
        }

        status(HttpStatusCode.Unauthorized){call, status ->
            call.respond(
                status,
                mapOf("error" to "Unauthorized")
            )
        }

        exception<Throwable>{call,cause ->
            cause.printStackTrace()

            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = mapOf(
                    "error" to "Internal Server Error"
                )
            )
        }
    }
}