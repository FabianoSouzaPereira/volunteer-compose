package com.fabianospdev.volunteerscompose.features.login.data


import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity
import com.fabianospdev.volunteerscompose.features.login.domain.repositories.LoginRepository
import kotlinx.coroutines.delay

class FakeLoginRepository(
    private val shouldSucceed: Boolean = true
) : LoginRepository {

    override suspend fun getLogin(email: String, password: String): Result<LoginResponseEntity> {
        delay(100)
        return if (shouldSucceed) {
            Result.success(LoginResponseEntity(id = "1", name = "Fabiano", email = "", token = "abc123"))
        } else {
            Result.failure(Exception("Login failed"))
        }
    }
}
