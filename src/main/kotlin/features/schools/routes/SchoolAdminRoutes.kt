package com.example.features.schools.routes

import com.example.core.plugins.exception.BadRequestException
import com.example.features.schools.dto.request.SchoolRequest
import com.example.features.schools.dto.request.UpdateSchoolRequest
import com.example.features.schools.model.SchoolModel
import com.example.features.schools.service.SchoolService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import java.util.UUID

fun Route.schoolAdminRoutes(schoolService: SchoolService) {

    route("/admin/schools") {

        get {
            val schools = schoolService.getAllSchools()
            call.respond(HttpStatusCode.OK,schools)
        }

        post {
            val req = call.receive<SchoolRequest>()
            val response = schoolService.createSchool(req)
            call.respond(HttpStatusCode.Created, response)
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: throw BadRequestException("missing id")
            schoolService.deleteSchool(UUID.fromString(id))
            call.respond(HttpStatusCode.NoContent)
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: throw BadRequestException("missing id")
            val req = call.receive<UpdateSchoolRequest>()
            val updatedSchool = schoolService.updateSchool(UUID.fromString(id), req)
            call.respond(HttpStatusCode.OK, updatedSchool)
        }
    }
}