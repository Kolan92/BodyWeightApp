package com.bodyweightapp.webapi.backend

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class MessageOfTheDayController {
    @GetMapping("/api/messages")
    fun messages(): List<Message> {
        return listOf(
                Message("I am a robot."),
                Message("Hello, world!")
        )
    }
}

