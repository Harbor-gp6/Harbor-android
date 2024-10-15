package com.example.apl_mobile_harbor.classes.auth

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String, manterConectado: Boolean) {
        TokenProvider.token = token
        if (manterConectado) {
            prefs.edit().putString("auth_token", token).apply()
        }
    }

    fun getTokenFromPrefs(): String? {
        return prefs.getString("auth_token", null)
    }

    fun getToken(): String? {
        return TokenProvider.token
    }

    fun clearToken() {
        prefs.edit().remove("auth_token").apply()
        TokenProvider.token = null
    }
}
