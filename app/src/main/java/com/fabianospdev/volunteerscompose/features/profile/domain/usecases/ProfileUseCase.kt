package com.fabianospdev.volunteerscompose.features.profile.domain.usecases

import com.fabianospdev.volunteerscompose.features.profile.domain.entities.ProfileResponseEntity

interface ProfileUseCase {
    suspend fun getProfile(): Result<ProfileResponseEntity>
    suspend fun saveProfile(profile: ProfileResponseEntity): Result<Unit>
}
