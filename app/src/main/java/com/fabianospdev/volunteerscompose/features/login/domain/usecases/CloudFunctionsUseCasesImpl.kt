package com.fabianospdev.volunteerscompose.features.login.domain.usecases

import com.fabianospdev.volunteerscompose.features.login.domain.repositories.CloudFunctionsRepository
import jakarta.inject.Inject

class CloudFunctionsUseCasesImpl @Inject constructor(
    private val cloudFunctionsRepository: CloudFunctionsRepository
) : CloudFunctionsUseCases {

    override suspend fun onUserCreate(userId: String): Result<Unit> {
        return cloudFunctionsRepository.callFunction(
            functionName = "onUserCreate",
            data = mapOf("userId" to userId)
        )
    }

    override suspend fun onUserUpdate(
        userId: String,
        changes: Map<String, Any>
    ): Result<Unit> {
        return cloudFunctionsRepository.callFunction(
            functionName = "onUserUpdate",
            data = mapOf(
                "userId" to userId,
                "changes" to changes
            )
        )
    }

    override suspend fun createAppointment(appointmentData: Map<String, Any>): Result<String> {
        return cloudFunctionsRepository.callFunctionWithResult(
            functionName = "createAppointment",
            data = appointmentData,
            resultType = String::class.java
        )
    }

    override suspend fun cancelAppointment(
        appointmentId: String,
        reason: String
    ): Result<Unit> {
        return cloudFunctionsRepository.callFunction(
            functionName = "cancelAppointment",
            data = mapOf(
                "appointmentId" to appointmentId,
                "reason" to reason
            )
        )
    }

    override suspend fun sendAppointmentReminder(appointmentId: String): Result<Unit> {
        return cloudFunctionsRepository.callFunction(
            functionName = "sendAppointmentReminder",
            data = mapOf("appointmentId" to appointmentId)
        )
    }

    override suspend fun generateReport(
        reportType: String,
        startDate: String,
        endDate: String
    ): Result<String> {
        return cloudFunctionsRepository.callFunctionWithResult(
            functionName = "generateReport",
            data = mapOf(
                "reportType" to reportType,
                "startDate" to startDate,
                "endDate" to endDate
            ),
            resultType = String::class.java
        )
    }

    override suspend fun backupData(): Result<String> {
        return cloudFunctionsRepository.callFunctionWithResult(
            functionName = "backupData",
            data = emptyMap(),
            resultType = String::class.java
        )
    }
}