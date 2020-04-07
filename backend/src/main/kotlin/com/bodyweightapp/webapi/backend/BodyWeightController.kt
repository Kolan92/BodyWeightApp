package com.bodyweightapp.webapi.backend

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BodyWeightController{
    @GetMapping("/api/bodyinfo")
    fun getBodyInfo(): List<BodyInfo> = listOf(
            BodyInfo(87.0, 1.9),
            BodyInfo(86.0, 1.9),
            BodyInfo(85.0, 1.9)
    )

}

data class BodyInfo (val weight: Double, val height: Double){
    val bmi: Double
        get() = weight / (height * height)
}

