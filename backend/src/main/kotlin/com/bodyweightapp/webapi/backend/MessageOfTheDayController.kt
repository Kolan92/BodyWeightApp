package com.bodyweightapp.webapi.backend

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageOfTheDayController {
    @GetMapping("/api/v1/messages")
    fun messages(): List<Message> {
        return listOf(
                Message("I am a robot."),
                Message("Hello, world!")
        )
    }
}
