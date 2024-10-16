package com.example.apl_mobile_harbor.classes.auth

import android.content.Context
import android.content.SharedPreferences
import com.example.apl_mobile_harbor.interfaces.ApiHarbor

class TokenManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String, manterConectado: Boolean) {
        val editor = prefs.edit()
        TokenProvider.token = token
        editor.putBoolean("manterConectado", manterConectado)
        if (manterConectado) {
            editor.putString("token", token)
        }
        editor.apply()
    }

    fun getTokenFromPrefs(): String? {
        return prefs.getString("token", null)
    }

    fun isUserLoggedIn(): Boolean {
        return prefs.getBoolean("manterConectado", false) && getTokenFromPrefs() != null
    }

    fun getToken(): String? {
        return TokenProvider.token
    }

    fun clearToken() {
        val editor = prefs.edit()
        editor.remove("token")
        editor.remove("manterConectado")
        editor.apply()
        TokenProvider.token = null
    }
}
