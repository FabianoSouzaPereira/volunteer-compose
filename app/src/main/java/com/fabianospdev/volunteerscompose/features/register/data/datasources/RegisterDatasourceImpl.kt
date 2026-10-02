package com.fabianospdev.volunteerscompose.features.register.data.datasources

import com.fabianospdev.volunteerscompose.core.helpers.exceptions.ValidationException
import com.fabianospdev.volunteerscompose.core.helpers.exceptions.toRequestException
import com.fabianospdev.volunteerscompose.features.register.data.models.RegisterResponseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterDatasourceImpl @Inject constructor() : RegisterDatasource {

    private val registeredEmails = mutableSetOf<String>()

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<RegisterResponseModel> {
        return try {
            val key = email.trim().lowercase()
            synchronized(registeredEmails) {
                if (!registeredEmails.add(key)) {
                    return Result.failure(ValidationException("E-mail já cadastrado"))
                }
            }
            Result.success(
                RegisterResponseModel(
                    name = name.trim(),
                    email = key,
                    message = "Conta criada para $name"
                )
            )
        } catch (e: Exception) {
            Result.failure(e.toRequestException())
        }
    }
}
