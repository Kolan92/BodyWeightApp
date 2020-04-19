package com.bodyweightapp.webapi.backend.controllers

import com.bodyweightapp.webapi.backend.SwaggerAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BodyWeightController{

    @SwaggerAuthorize
    @GetMapping("/api/v1/bodyinfo")
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

