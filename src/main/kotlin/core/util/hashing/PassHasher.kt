package com.example.core.util.hashing

interface PassHasher {

    fun hashPassword(password: String): String

    fun verifyPassword(password: String, hashedPassword: String): Boolean
}