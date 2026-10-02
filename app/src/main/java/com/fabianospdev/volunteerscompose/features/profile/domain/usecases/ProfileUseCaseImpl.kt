package com.fabianospdev.volunteerscompose.features.profile.domain.usecases

import com.fabianospdev.volunteerscompose.features.profile.domain.entities.ProfileResponseEntity
import com.fabianospdev.volunteerscompose.features.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class ProfileUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : ProfileUseCase {
    override suspend fun getProfile(): Result<ProfileResponseEntity> {
        return repository.getProfile()
    }

    override suspend fun saveProfile(profile: ProfileResponseEntity): Result<Unit> {
        return repository.saveProfile(profile)
    }
}
