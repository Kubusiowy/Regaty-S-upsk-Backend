package com.example.core.plugins


import com.example.features.auth.routes.authRoutes
import com.example.features.auth.service.AuthService
import com.example.features.categories.routes.categoryAdminRoutes
import com.example.features.categories.service.CategoryService
import com.example.features.schools.routes.schoolAdminRoutes
import com.example.features.schools.service.SchoolService
import com.example.features.scores.routes.scoreAdminRoutes
import com.example.features.scores.routes.scoreClientRoute
import com.example.features.scores.service.ResultService
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.http.content.staticResources
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val authService by inject<AuthService>()
    val resultService by inject<ResultService>()
    val categoryService by inject<CategoryService>()
    val schoolService by inject<SchoolService>()
    routing {
        staticResources("/", "static"){
            default("index.html")
        }

        authenticate("auth-jwt") {
            scoreAdminRoutes(resultService)
            categoryAdminRoutes(categoryService)
            schoolAdminRoutes(schoolService)
        }

        scoreClientRoute(resultService)

        authRoutes(authService)

    }
}
