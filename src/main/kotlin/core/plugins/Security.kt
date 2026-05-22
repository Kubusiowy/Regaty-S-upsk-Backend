package com.example.core.plugins

import com.example.core.plugins.security.JwtService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {

    val jwtRealm = environment.config.property("jwt.realm").getString()
    val jwtService by inject<JwtService>()
    authentication {
        jwt("auth-jwt") {
            realm = jwtRealm
            verifier(jwtService.verifier)
            validate { credential ->
                val userId = credential.payload.getClaim("userId").asString()
                val type = credential.payload.getClaim("type").asString()

                if(userId == null || type.isEmpty()) null else JWTPrincipal(credential.payload)
            }
        }
    }
}
