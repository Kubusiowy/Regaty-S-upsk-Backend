package com.example.core.plugins

import com.example.core.database.koin.databaseModule
import com.example.core.plugins.security.koin.securityModule
import com.example.core.util.koin.utilModule
import com.example.features.auth.di.authModule
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            single { environment.config }
        },
            databaseModule,
            securityModule,
            authModule,
            utilModule,
            )
    }
}
