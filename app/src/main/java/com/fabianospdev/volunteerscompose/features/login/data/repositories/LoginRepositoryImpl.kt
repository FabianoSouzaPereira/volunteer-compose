package com.fabianospdev.volunteerscompose.features.login.data.repositories

import com.fabianospdev.volunteerscompose.features.login.data.models.toEntity
import com.fabianospdev.volunteerscompose.features.login.domain.datasources.LoginDatasource
import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity
import com.fabianospdev.volunteerscompose.features.login.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDatasource: LoginDatasource
) : LoginRepository {

    override suspend fun getLogin(email: String, password: String): Result<LoginResponseEntity> {
        return try {
            val responseModel = loginDatasource.getLogin(email, password).getOrThrow()
            Result.success(responseModel.toEntity())
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}
