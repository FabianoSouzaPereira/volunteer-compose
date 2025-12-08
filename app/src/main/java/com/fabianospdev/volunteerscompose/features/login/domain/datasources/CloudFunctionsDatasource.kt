package com.fabianospdev.volunteerscompose.features.login.domain.datasources

interface CloudFunctionsDatasource {
    suspend fun callFunction(functionName: String,data: Map<String, Any>): Result<Unit>
    suspend fun <T> callFunctionWithResult(functionName: String, data: Map<String, Any>,resultType: Class<T>): Result<T>
}