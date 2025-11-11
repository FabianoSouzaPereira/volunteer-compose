package com.fabianospdev.volunteerscompose.features.login.domain.usecases

import com.fabianospdev.volunteerscompose.features.login.domain.entities.LoginResponseEntity

class FakeLoginUsecase(
    private val fakeLoginRepository: com.fabianospdev.volunteerscompose.features.login.data.FakeLoginRepository
) : LoginUsecase {

    override suspend fun getLogin(email: String, password: String): Result<LoginResponseEntity> {
        return fakeLoginRepository.getLogin(email, password)
    }
}