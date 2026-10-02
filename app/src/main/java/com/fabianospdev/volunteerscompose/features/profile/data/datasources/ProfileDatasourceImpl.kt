package com.fabianospdev.volunteerscompose.features.profile.data.datasources

import com.fabianospdev.volunteerscompose.core.helpers.exceptions.ValidationException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.toRequestException
import com.fabianospdev.volunteerscompose.features.profile.data.models.ProfileModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileDatasourceImpl @Inject constructor() : ProfileDatasource {

    @Volatile
    private var current = ProfileModel(
        name = "Usuário",
        email = "usuario@email.com",
        phone = ""
    )

    override suspend fun getProfile(): Result<ProfileModel> {
        return try {
            Result.success(current)
        } catch (e: Exception) {
            Result.failure(e.toRequestException())
        }
    }

    override suspend fun saveProfile(profile: ProfileModel): Result<Unit> {
        return try {
            if (profile.name.isBlank()) {
                return Result.failure(ValidationException("Nome é obrigatório"))
            }
            current = profile.copy(name = profile.name.trim(), phone = profile.phone.trim())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e.toRequestException())
        }
    }
}
