package com.fabianospdev.volunteerscompose.features.login.domain.usecases

import com.fabianospdev.volunteerscompose.features.login.data.models.FileUploadRequest
import com.fabianospdev.volunteerscompose.features.login.data.models.FileUploadResponse

interface StorageUseCases {
    suspend fun uploadFile(request: FileUploadRequest): Result<FileUploadResponse>
    suspend fun downloadFile(filePath: String): Result<ByteArray>
    suspend fun deleteFile(filePath: String): Result<Unit>
    suspend fun getFileMetadata(filePath: String): Result<Map<String, Any>>
    suspend fun updateFileMetadata(filePath: String, metadata: Map<String, Any>): Result<Unit>

    // Upload específicos
    suspend fun uploadUserProfileImage(userId: String, imageBytes: ByteArray): Result<FileUploadResponse>
    suspend fun uploadInstitutionLogo(institutionId: String, imageBytes: ByteArray): Result<FileUploadResponse>
    suspend fun uploadEventImage(eventId: String, imageBytes: ByteArray): Result<FileUploadResponse>
}