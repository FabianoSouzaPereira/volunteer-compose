package com.fabianospdev.baseapp.features.login.domain.usecases

import com.fabianospdev.baseapp.features.login.domain.entities.LoginResponseEntity
import com.fabianospdev.baseapp.features.login.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginUsecaseImpl @Inject constructor(
    private val repository: LoginRepository
) : LoginUsecase {

    override suspend fun getLogin(email: String, password: String): Result<LoginResponseEntity> {
        return repository.getLogin(email, password)
    }
}