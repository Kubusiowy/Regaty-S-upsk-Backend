package com.example.features.scores.routes

import com.example.features.scores.service.ResultService
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.scoreClientRoute(resultService: ResultService) {

    route("/client/scores") {

        get{
            val results = resultService.getAllResults()
            call.respond(HttpStatusCode.OK,results)
        }

        get("/ranking"){
            val ranking = resultService.getResultRanking()
            call.respond(HttpStatusCode.OK,ranking)
        }

    }

}