package com.fabianospdev.volunteerscompose.features.login.domain.usecases

interface CloudFunctionsUseCases {
    suspend fun onUserCreate(userId: String): Result<Unit>
    suspend fun onUserUpdate(userId: String, changes: Map<String, Any>): Result<Unit>
    suspend fun createAppointment(appointmentData: Map<String, Any>): Result<String>
    suspend fun cancelAppointment(appointmentId: String, reason: String): Result<Unit>
    suspend fun sendAppointmentReminder(appointmentId: String): Result<Unit>
    suspend fun generateReport(reportType: String, startDate: String, endDate: String): Result<String>
    suspend fun backupData(): Result<String>
}