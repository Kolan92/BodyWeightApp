package com.bodyweightapp.webapi.backend.controllers

import com.bodyweightapp.webapi.backend.data.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*


@RestController
class UserProfileController (
        @Autowired
        private val profileRepository: ProfileRepository
    ){

    @GetMapping("/api/v1/profile")
    @ResponseBody
    fun getProfile(): ResponseEntity<ProfileModel> {
        val authetication = SecurityContextHolder.getContext().authentication
        val uid = (authetication.credentials as Jwt).claims["uid"] as String

        val profile = profileRepository.getProfile(uid)
        if (profile is Profile)
            return ResponseEntity(profile.asModel(), HttpStatus.OK)
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/api/v1/profile")
    @ResponseBody
    fun upsertProfile(@RequestBody profile: ProfileModel) {
        val authetication = SecurityContextHolder.getContext().authentication
        val uid = (authetication.credentials as Jwt).claims["uid"] as String

        profileRepository.upsertProfile(profile.asEntity(uid))
    }
}