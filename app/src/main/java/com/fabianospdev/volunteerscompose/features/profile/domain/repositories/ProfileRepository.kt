package com.fabianospdev.volunteerscompose.features.profile.domain.repositories

import com.fabianospdev.volunteerscompose.features.profile.domain.entities.ProfileResponseEntity

interface ProfileRepository {
    suspend fun getProfile(): Result<ProfileResponseEntity>
    suspend fun saveProfile(profile: ProfileResponseEntity): Result<Unit>
}
