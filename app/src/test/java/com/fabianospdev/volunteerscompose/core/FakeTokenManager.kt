package com.fabianospdev.volunteerscompose.core

import com.fabianospdev.volunteerscompose.core.helpers.TokenManager

class FakeTokenManager : TokenManager {
    var savedToken: String? = null
        private set

    override fun saveToken(token: String) {
        savedToken = token
    }

    override fun getToken(): String? = savedToken

    override fun clearToken() {
        savedToken = null
    }
}
