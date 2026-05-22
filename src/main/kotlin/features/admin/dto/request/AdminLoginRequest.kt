package com.example.features.admin.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class AdminLoginRequest(
    val login:String,
    val rawPassword:String
)