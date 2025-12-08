package com.fabianospdev.volunteerscompose.features.login.data.models

data class FileUploadRequest(
    val filePath: String,
    val fileBytes: ByteArray,
    val contentType: String,
    val metadata: Map<String, Any> = emptyMap()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileUploadRequest

        if (filePath != other.filePath) return false
        if (!fileBytes.contentEquals(other.fileBytes)) return false
        if (contentType != other.contentType) return false
        if (metadata != other.metadata) return false

        return true
    }

    override fun hashCode(): Int {
        var result = filePath.hashCode()
        result = 31 * result + fileBytes.contentHashCode()
        result = 31 * result + contentType.hashCode()
        result = 31 * result + metadata.hashCode()
        return result
    }
}
