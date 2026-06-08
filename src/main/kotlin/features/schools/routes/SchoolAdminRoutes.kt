package com.example.features.schools.routes

import com.example.features.schools.service.SchoolService
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.schoolAdminRoutes(schoolService: SchoolService) {

    route("/admin/schools") {

        get {

        }

        post {

        }

        delete {

        }

        put {

        }
    }
}