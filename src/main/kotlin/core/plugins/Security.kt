package com.example.core.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.core.plugins.security.JwtService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.ApplicationConfig
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {

    val jwtRealm = environment.config.property("jwt.realm").getString()
    val jwtService by inject<JwtService>()
    authentication {
        jwt {
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
