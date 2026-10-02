package com.fabianospdev.volunteerscompose.features.profile.data.datasources

import com.fabianospdev.volunteerscompose.features.profile.data.models.ProfileModel

interface ProfileDatasource {
    suspend fun getProfile(): Result<ProfileModel>
    suspend fun saveProfile(profile: ProfileModel): Result<Unit>
}
