package com.example.features.scores.routes


import com.example.features.scores.dto.request.CreateResultRequest
import com.example.features.scores.dto.request.UpdateResultRequest
import com.example.features.scores.service.ResultService
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import java.util.UUID


fun Route.scoreAdminRoutes(resultService: ResultService) {

    route("/admin/scores") {


        post("/add") {
            val req = call.receive<CreateResultRequest>()
            val response = resultService.createResult(req)
            call.respond(HttpStatusCode.OK,response)
        }

        delete("/{resultId}") {
            val id = call.parameters["resultId"] ?: throw BadRequestException("result id missing")
            resultService.deleteResult(UUID.fromString(id))
            call.respond(HttpStatusCode.NoContent)
        }

        put("/{resultId}") {
            val id = call.parameters["resultId"] ?: throw BadRequestException("result id missing")
            val req = call.receive<UpdateResultRequest>()
            val response = resultService.updateResult(UUID.fromString(id), req)
            call.respond(HttpStatusCode.OK,response)

        }
    }
}