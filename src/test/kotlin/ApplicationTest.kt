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
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
        client.get("/panel/panel.html").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
        client.get("/autorzy/autorzy.html").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
        client.get("/regaty/1.jpg").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

}
