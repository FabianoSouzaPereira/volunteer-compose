package com.fabianospdev.volunteerscompose.core.helpers

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface TokenManager {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}

@Singleton
class EncryptedTokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) : TokenManager {

    private val prefs: SharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun saveToken(token: String) {
        prefs.edit().putString(TOKEN_KEY, token).apply()
    }

    override fun getToken(): String? = prefs.getString(TOKEN_KEY, null)

    override fun clearToken() {
        prefs.edit().remove(TOKEN_KEY).apply()
    }

    private companion object {
        const val FILE_NAME = "secure_prefs"
        const val TOKEN_KEY = "token"
    }
}
