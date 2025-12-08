package com.fabianospdev.volunteerscompose.features.login.domain.usecases

interface FirestoreUseCases {
    // Users
    suspend fun createUserDocument(userId: String, data: Map<String, Any>): Result<String>
    suspend fun getUserDocument(userId: String): Result<Map<String, Any>>
    suspend fun updateUserDocument(userId: String, data: Map<String, Any>): Result<Unit>
    suspend fun deleteUserDocument(userId: String): Result<Unit>
    suspend fun queryUserDocuments(field: String, value: Any): Result<List<Map<String, Any>>>

    // Appointments
    suspend fun createAppointmentDocument(data: Map<String, Any>): Result<String>
    suspend fun getAppointmentDocument(appointmentId: String): Result<Map<String, Any>>
    suspend fun updateAppointmentDocument(appointmentId: String, data: Map<String, Any>): Result<Unit>
    suspend fun deleteAppointmentDocument(appointmentId: String): Result<Unit>
    suspend fun queryAppointmentsByUser(userId: String): Result<List<Map<String, Any>>>
    suspend fun queryAppointmentsByDateRange(startDate: String, endDate: String): Result<List<Map<String, Any>>>

    // Institutions
    suspend fun createInstitutionDocument(data: Map<String, Any>): Result<String>
    suspend fun getInstitutionDocument(institutionId: String): Result<Map<String, Any>>
    suspend fun updateInstitutionDocument(institutionId: String, data: Map<String, Any>): Result<Unit>
    suspend fun deleteInstitutionDocument(institutionId: String): Result<Unit>
    suspend fun queryInstitutionsByType(type: String): Result<List<Map<String, Any>>>

    // Volunteers
    suspend fun createVolunteerDocument(data: Map<String, Any>): Result<String>
    suspend fun getVolunteerDocument(volunteerId: String): Result<Map<String, Any>>
    suspend fun updateVolunteerDocument(volunteerId: String, data: Map<String, Any>): Result<Unit>
    suspend fun deleteVolunteerDocument(volunteerId: String): Result<Unit>
    suspend fun queryVolunteersBySkill(skill: String): Result<List<Map<String, Any>>>

    // Batch Operations
    suspend fun batchGetDocuments(documentIds: List<String>): Result<List<Map<String, Any>>>
    suspend fun batchWriteDocuments(operations: List<Map<String, Any>>): Result<Unit>
}