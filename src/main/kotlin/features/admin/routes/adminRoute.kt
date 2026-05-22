package com.example.features.admin.routes

import com.example.features.admin.dto.request.AdminLoginRequest
import com.example.features.admin.dto.response.AdminLoginResponse
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.adminRoutes(){

     route("/admin"){

         get{
             call.respond("sigma")
         }

         post("/login") {
            val adminCredentials: AdminLoginRequest = call.receive<AdminLoginRequest>()
         }

         authenticate("auth-jwt") {

         }
     }

}