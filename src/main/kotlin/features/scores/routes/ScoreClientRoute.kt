package com.example.features.scores.routes

import com.example.features.scores.service.ResultService
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.scoreClientRoute(resultService: ResultService) {

    route("/client/scores") {

    }

}