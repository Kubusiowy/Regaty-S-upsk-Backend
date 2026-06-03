package com.example.core.util.sha

import java.security.MessageDigest

fun String.sha256(): String {
    return MessageDigest.getInstance("SHA-256")
        .digest(this.toByteArray())
        .joinToString("") { "%02x".format(it) }
}