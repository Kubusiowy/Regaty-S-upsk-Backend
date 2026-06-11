package com.example.features.schools.routes

import com.example.features.schools.service.SchoolService
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.schoolClientRoutes(schoolService: SchoolService) {

    route("/client/schools"){
        get{
                val schools = schoolService.getAllSchools()
                call.respond(HttpStatusCode.OK,schools)
        }
    }
}