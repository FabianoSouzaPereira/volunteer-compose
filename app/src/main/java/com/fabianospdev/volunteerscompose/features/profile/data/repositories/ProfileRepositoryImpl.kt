package com.fabianospdev.volunteerscompose.features.profile.data.repositories

import com.fabianospdev.volunteerscompose.features.profile.data.datasources.ProfileDatasource
import com.fabianospdev.volunteerscompose.features.profile.data.models.toEntity
import com.fabianospdev.volunteerscompose.features.profile.data.models.toModel
import com.fabianospdev.volunteerscompose.features.profile.domain.entities.ProfileResponseEntity
import com.fabianospdev.volunteerscompose.features.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDatasource: ProfileDatasource
) : ProfileRepository {
    override suspend fun getProfile(): Result<ProfileResponseEntity> {
        return profileDatasource.getProfile().map { it.toEntity() }
    }

    override suspend fun saveProfile(profile: ProfileResponseEntity): Result<Unit> {
        return profileDatasource.saveProfile(profile.toModel())
    }
}
