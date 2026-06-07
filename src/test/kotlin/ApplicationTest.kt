package com.example

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        environment {
            config = MapApplicationConfig(
                "jwt.realm" to "test",
                "jwt.domain" to "test",
                "jwt.audience" to "test",
                "jwt.secret" to "test-secret",
            )
        }
        application {
            module(enableDatabase = false)
        }

    }

}
