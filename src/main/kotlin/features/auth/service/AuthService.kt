package com.example.features.auth.service

import com.example.features.auth.domain.DTO.Request.AdminLoginRequest
import com.example.features.auth.domain.model.LoginResult

interface AuthService {

    suspend fun login(adminLoginRequest: AdminLoginRequest): LoginResult


}