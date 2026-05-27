package com.example.features.admin.routes

import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.adminRoutes(){

     route("/admin"){

         get{
             call.respond("sigma")
         }


     }

}