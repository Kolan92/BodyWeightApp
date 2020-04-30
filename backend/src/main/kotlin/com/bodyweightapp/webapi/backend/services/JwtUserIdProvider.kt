package com.bodyweightapp.webapi.backend.services

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service

interface UserIdProvider {
    fun getUserId(): String
}

@Service
class JwtUserIdProvider : UserIdProvider {
    override fun getUserId(): String {
        val authetication = SecurityContextHolder.getContext().authentication
        return (authetication.credentials as Jwt).claims["uid"] as String
    }
}