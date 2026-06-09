package com.example.features.scores.routes


import com.example.features.scores.dto.request.CreateResultRequest
import com.example.features.scores.service.ResultService
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route



fun Route.scoreAdminRoutes(resultService: ResultService) {

    route("/admin/scores") {
        get{
            call.respond("test udany score admin")

        }

        post("/add") {
            val req = call.receive<CreateResultRequest>()
        }

        delete("/{resultId}") {

        }

        put("/{resultId}") {

        }
    }
}