package com.fabianospdev.volunteerscompose.features.login.domain.repositories

interface FirestoreRepository {
    suspend fun createDocument(collection: String, documentId: String? = null, data: Map<String, Any>): Result<String>
    suspend fun getDocument(collection: String, documentId: String): Result<Map<String, Any>>
    suspend fun updateDocument(collection: String, documentId: String, data: Map<String, Any>): Result<Unit>
    suspend fun deleteDocument(collection: String, documentId: String): Result<Unit>
    suspend fun queryDocuments(collection: String, field: String, value: Any, limit: Int? = null): Result<List<Map<String, Any>>>
    suspend fun queryDocumentsWithRange(collection: String, field: String, startValue: Any, endValue: Any): Result<List<Map<String, Any>>>

    // Operações em lote
    suspend fun batchGet(documentIds: List<String>): Result<List<Map<String, Any>>>
    suspend fun batchWrite(operations: List<Map<String, Any>>): Result<Unit>
}