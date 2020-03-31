package com.bodyweightapp.webapi.backend

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class MessageOfTheDayController {
    @GetMapping("/api/messages")
    @CrossOrigin(origins = ["http://localhost:8080"])
    fun messages(): List<Message> {
        return listOf(
                Message("I am a robot."),
                Message("Hello, world!")
        )
    }
}