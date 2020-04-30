package com.bodyweightapp.webapi.backend.data

import org.joda.time.DateTime
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

interface ProfileRepository {
    fun getProfile(uid: String): Profile?
    fun upsertProfile(profile: Profile)
}

@Repository
class InMemoryProfileRepository: ProfileRepository{
    val profilesMap = ConcurrentHashMap<String, Profile>()

    override fun getProfile(userId: String) = profilesMap[userId]

    override fun upsertProfile(profile: Profile) {
        profilesMap[profile.userId] = profile
    }

}

data class Profile(
        val userId: String,
        val height: Int,
        val birthDate: DateTime
)

data class ProfileModel(
        val height: Int,
        val birthDate: DateTime
)

fun Profile.asModel() = ProfileModel(height, birthDate)
fun ProfileModel.asEntity(userId: String) = Profile(userId, height, birthDate)