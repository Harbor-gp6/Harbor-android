package com.example.apl_mobile_harbor.classes.auth

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class TokenManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveToken(token: String, manterConectado: Boolean) {
        val editor = prefs.edit()
        TokenProvider.token = token
        editor.putBoolean("manterConectado", manterConectado)
        if (manterConectado) {
            editor.putString("token", token)
        }
        editor.apply()
    }

    fun saveUsuario(usuario: Usuario) {
        val editor = prefs.edit()
        val usuarioJson = gson.toJson(usuario)
        editor.putString("usuario", usuarioJson)
        editor.apply()
    }

    fun getUsuario(): Usuario? {
        val usuarioJson = prefs.getString("usuario", null)
        return if (usuarioJson != null) {
            gson.fromJson(usuarioJson, Usuario::class.java)
        } else {
            null
        }
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
        editor.remove("usuario")
        editor.apply()
        TokenProvider.token = null
    }
}
