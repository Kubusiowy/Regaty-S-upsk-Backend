package com.example.features.categories.routes

import com.example.features.categories.service.CategoryService
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.categoryClientRoutes(categoryService: CategoryService) {

    route("/client/categories") {
        get{
            val categories = categoryService.getCategories()
            call.respond(HttpStatusCode.OK,categories)
        }
    }

}