package com.bodyweightapp.webapi.backend.controllers

import com.bodyweightapp.webapi.backend.data.*
import com.bodyweightapp.webapi.backend.services.JwtUserIdProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class UserProfileController (
        @Autowired
        private val profileRepository: ProfileRepository,
        @Autowired
        private val userIdProvider: JwtUserIdProvider
    ) {

    @GetMapping("/api/v1/profile")
    @ResponseBody
    fun getProfile(): ResponseEntity<ProfileModel> {
        val userId = userIdProvider.getUserId()

        val profile = profileRepository.getProfile(userId)
        if (profile is Profile)
            return ResponseEntity(profile.asModel(), HttpStatus.OK)
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/api/v1/profile")
    @ResponseBody
    fun upsertProfile(@RequestBody profile: ProfileModel) {
        val userId = userIdProvider.getUserId()

        profileRepository.upsertProfile(profile.asEntity(userId))
    }
}

