package com.fabianospdev.volunteerscompose.core.helpers
/*
* Advantages of this approach:
* Encapsulation: Access to the token is restricted to this TokenManager class, which makes the code more secure.
* Ease of maintenance: If you need to modify the way the token is saved or retrieved, simply change the implementation within this class.
* Testability: Since token handling is centralized, it is easier to create unit tests to ensure that the class's behavior is correct.
*/

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object TokenManager {
    private const val TOKEN_KEY = "token"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return EncryptedSharedPreferences.create(
            "secure_prefs",
            masterKey.toString(),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveToken(context: Context, token: String) {
        val sharedPreferences = getSharedPreferences(context)
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(context: Context): String? {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(TOKEN_KEY, null)
    }
}
