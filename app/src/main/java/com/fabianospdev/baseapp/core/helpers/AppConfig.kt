package com.fabianospdev.baseapp.core.helpers

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppConfig(initialState: Boolean, private val context: Context) {
    private val _isUsingFirebaseStateFlow = MutableStateFlow(initialState)
    val isUsingFirebase: StateFlow<Boolean> get() = _isUsingFirebaseStateFlow

    private val _baseUrl = MutableStateFlow("")
    val baseUrl: StateFlow<String> get() = _baseUrl

    private fun setBaseUrlState(isUsing: String) {
        _baseUrl.value = isUsing
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("baseUrl", isUsing).apply()
    }

    fun getBaseUrlState(): Boolean {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("baseUrl", false)
    }

    fun setUsingFirebase(isUsing: Boolean) {
        _isUsingFirebaseStateFlow.value = isUsing
    }

    fun getIsUsingFirebase(): Boolean {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("is_using_firebase", false)
    }

    private fun saveState(isUsing: Boolean) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("is_using_firebase", isUsing).apply()
    }

    fun saveAdminClaim(adminClaim: Boolean) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("adminClaim", adminClaim).apply()
    }

    fun getAdminClaim(): Boolean {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("adminClaim", false)
    }
}