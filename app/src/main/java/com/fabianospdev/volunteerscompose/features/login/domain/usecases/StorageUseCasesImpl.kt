package com.fabianospdev.volunteerscompose.features.login.domain.usecases

import com.fabianospdev.volunteerscompose.features.login.data.models.FileUploadRequest
import com.fabianospdev.volunteerscompose.features.login.data.models.FileUploadResponse
import com.fabianospdev.volunteerscompose.features.login.domain.repositories.StorageRepository
import jakarta.inject.Inject

class StorageUseCasesImpl @Inject constructor(
    private val storageRepository: StorageRepository
) : StorageUseCases {

    override suspend fun uploadFile(request: FileUploadRequest): Result<FileUploadResponse> {
        return storageRepository.uploadFile(request)
    }

    override suspend fun downloadFile(filePath: String): Result<ByteArray> {
        return storageRepository.downloadFile(filePath)
    }

    override suspend fun deleteFile(filePath: String): Result<Unit> {
        return storageRepository.deleteFile(filePath)
    }

    override suspend fun getFileMetadata(filePath: String): Result<Map<String, Any>> {
        return storageRepository.getFileMetadata(filePath)
    }

    override suspend fun updateFileMetadata(
        filePath: String,
        metadata: Map<String, Any>
    ): Result<Unit> {
        return storageRepository.updateFileMetadata(filePath, metadata)
    }

    override suspend fun uploadUserProfileImage(
        userId: String,
        imageBytes: ByteArray
    ): Result<FileUploadResponse> {
        val filePath = String.format(
            "user_profiles/%s_%d.jpg",
            userId,
            System.currentTimeMillis()
        )

        val request = FileUploadRequest(
            filePath = filePath,
            fileBytes = imageBytes,
            contentType = "image/jpeg",
            metadata = mapOf(
                "userId" to userId,
                "uploadedAt" to System.currentTimeMillis()
            )
        )

        return storageRepository.uploadFile(request)
    }

    override suspend fun uploadInstitutionLogo(
        institutionId: String,
        imageBytes: ByteArray
    ): Result<FileUploadResponse> {
        val filePath = String.format(
            "institution_logos/%s_%d.png",
            institutionId,
            System.currentTimeMillis()
        )

        val request = FileUploadRequest(
            filePath = filePath,
            fileBytes = imageBytes,
            contentType = "image/png",
            metadata = mapOf(
                "institutionId" to institutionId,
                "uploadedAt" to System.currentTimeMillis()
            )
        )

        return storageRepository.uploadFile(request)
    }

    override suspend fun uploadEventImage(
        eventId: String,
        imageBytes: ByteArray
    ): Result<FileUploadResponse> {
        val filePath = String.format(
            "event_images/%s_%d.jpg",
            eventId,
            System.currentTimeMillis()
        )

        val request = FileUploadRequest(
            filePath = filePath,
            fileBytes = imageBytes,
            contentType = "image/jpeg",
            metadata = mapOf(
                "eventId" to eventId,
                "uploadedAt" to System.currentTimeMillis()
            )
        )

        return storageRepository.uploadFile(request)
    }
}