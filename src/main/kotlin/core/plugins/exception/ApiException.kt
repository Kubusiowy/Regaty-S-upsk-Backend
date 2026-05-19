package com.example.core.plugins.exception

import io.ktor.http.HttpStatusCode

sealed class ApiException(message: String, val status: HttpStatusCode) : RuntimeException(message)

class BadRequestException(message: String = "bad request") : ApiException(message, HttpStatusCode.BadRequest)
class UnauthorizedException(message: String = "unauthorized") : ApiException(message, HttpStatusCode.Unauthorized)
class ForbiddenException(message: String = "forbidden") : ApiException(message, HttpStatusCode.Forbidden)
class NotFoundException(message: String = "not found") : ApiException(message, HttpStatusCode.NotFound)
class ConflictException(message: String = "conflict") : ApiException(message, HttpStatusCode.Conflict)