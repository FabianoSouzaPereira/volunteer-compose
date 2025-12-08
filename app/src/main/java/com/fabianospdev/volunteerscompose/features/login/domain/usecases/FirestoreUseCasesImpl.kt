package com.fabianospdev.volunteerscompose.features.login.domain.usecases

import com.fabianospdev.volunteerscompose.features.login.domain.repositories.FirestoreRepository
import jakarta.inject.Inject

class FirestoreUseCasesImpl @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : FirestoreUseCases {

    // Users
    override suspend fun createUserDocument(userId: String, data: Map<String, Any>): Result<String> {
        return firestoreRepository.createDocument(
            collection = "users",
            documentId = userId,
            data = data
        )
    }

    override suspend fun getUserDocument(userId: String): Result<Map<String, Any>> {
        return firestoreRepository.getDocument("users", userId)
    }

    override suspend fun updateUserDocument(userId: String, data: Map<String, Any>): Result<Unit> {
        return firestoreRepository.updateDocument("users", userId, data)
    }

    override suspend fun deleteUserDocument(userId: String): Result<Unit> {
        return firestoreRepository.deleteDocument("users", userId)
    }

    override suspend fun queryUserDocuments(field: String, value: Any): Result<List<Map<String, Any>>> {
        return firestoreRepository.queryDocuments(
            collection = "users",
            field = field,
            value = value
        )
    }

    // Appointments
    override suspend fun createAppointmentDocument(data: Map<String, Any>): Result<String> {
        return firestoreRepository.createDocument("appointments", data = data)
    }

    override suspend fun getAppointmentDocument(appointmentId: String): Result<Map<String, Any>> {
        return firestoreRepository.getDocument("appointments", appointmentId)
    }

    override suspend fun updateAppointmentDocument(
        appointmentId: String,
        data: Map<String, Any>
    ): Result<Unit> {
        return firestoreRepository.updateDocument("appointments", appointmentId, data)
    }

    override suspend fun deleteAppointmentDocument(appointmentId: String): Result<Unit> {
        return firestoreRepository.deleteDocument("appointments", appointmentId)
    }

    override suspend fun queryAppointmentsByUser(userId: String): Result<List<Map<String, Any>>> {
        return firestoreRepository.queryDocuments(
            collection = "appointments",
            field = "userId",
            value = userId
        )
    }

    override suspend fun queryAppointmentsByDateRange(
        startDate: String,
        endDate: String
    ): Result<List<Map<String, Any>>> {
        return firestoreRepository.queryDocumentsWithRange(
            collection = "appointments",
            field = "date",
            startValue = startDate,
            endValue = endDate
        )
    }

    // Institutions
    override suspend fun createInstitutionDocument(data: Map<String, Any>): Result<String> {
        return firestoreRepository.createDocument("institutions", data = data)
    }

    override suspend fun getInstitutionDocument(institutionId: String): Result<Map<String, Any>> {
        return firestoreRepository.getDocument("institutions", institutionId)
    }

    override suspend fun updateInstitutionDocument(
        institutionId: String,
        data: Map<String, Any>
    ): Result<Unit> {
        return firestoreRepository.updateDocument("institutions", institutionId, data)
    }

    override suspend fun deleteInstitutionDocument(institutionId: String): Result<Unit> {
        return firestoreRepository.deleteDocument("institutions", institutionId)
    }

    override suspend fun queryInstitutionsByType(type: String): Result<List<Map<String, Any>>> {
        return firestoreRepository.queryDocuments(
            collection = "institutions",
            field = "type",
            value = type
        )
    }

    // Volunteers
    override suspend fun createVolunteerDocument(data: Map<String, Any>): Result<String> {
        return firestoreRepository.createDocument("volunteers", data = data)
    }

    override suspend fun getVolunteerDocument(volunteerId: String): Result<Map<String, Any>> {
        return firestoreRepository.getDocument("volunteers", volunteerId)
    }

    override suspend fun updateVolunteerDocument(
        volunteerId: String,
        data: Map<String, Any>
    ): Result<Unit> {
        return firestoreRepository.updateDocument("volunteers", volunteerId, data)
    }

    override suspend fun deleteVolunteerDocument(volunteerId: String): Result<Unit> {
        return firestoreRepository.deleteDocument("volunteers", volunteerId)
    }

    override suspend fun queryVolunteersBySkill(skill: String): Result<List<Map<String, Any>>> {
        return firestoreRepository.queryDocuments(
            collection = "volunteers",
            field = "skills",
            value = skill
        )
    }

    // Batch Operations
    override suspend fun batchGetDocuments(documentIds: List<String>): Result<List<Map<String, Any>>> {
        return firestoreRepository.batchGet(documentIds)
    }

    override suspend fun batchWriteDocuments(operations: List<Map<String, Any>>): Result<Unit> {
        return firestoreRepository.batchWrite(operations)
    }
}