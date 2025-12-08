package com.fabianospdev.volunteerscompose.features.login.data.models

data class FileUploadResponse(
    val downloadUrl: String,
    val filePath: String,
    val contentType: String,
    val size: Long,
    val metadata: Map<String, Any> = emptyMap()
)