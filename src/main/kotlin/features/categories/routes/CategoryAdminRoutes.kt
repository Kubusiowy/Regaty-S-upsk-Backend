package com.example.features.categories.routes

import com.example.core.plugins.exception.BadRequestException
import com.example.features.categories.dto.request.CreateCategoryRequest
import com.example.features.categories.dto.request.UpdateCategoryRequest
import com.example.features.categories.service.CategoryService
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

fun Route.categoryAdminRoutes(categoryService: CategoryService) {

    route("/admin/categories") {

        get{
            val categories = categoryService.getCategories()
            call.respond(HttpStatusCode.OK,categories)
        }

        post {
            val req = call.receive<CreateCategoryRequest>()
            val response = categoryService.createCategory(req)
            call.respond(HttpStatusCode.Created,response)
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: throw BadRequestException("Missing ID")
             categoryService.deleteCategory(UUID.fromString(id))
            call.respond(HttpStatusCode.OK)
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: throw BadRequestException("Missing ID")
            val req = call.receive<UpdateCategoryRequest>()
            val response = categoryService.updateCategory(UUID.fromString(id),req)
            call.respond(HttpStatusCode.OK,response)
        }
    }
}

