package com.example.apl_mobile_harbor.classes.auth

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class TokenManager(context: Context, private var usuario: Usuario?) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveToken(usuario: Usuario, manterConectado: Boolean) {
        val editor = prefs.edit()
        this.usuario = usuario
        val usuarioJson = gson.toJson(usuario)
        editor.putString("usuario", usuarioJson)
        editor.putBoolean("manterConectado", manterConectado)
        if (manterConectado) {
            editor.putString("token", usuario.token)
        }
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
        return usuario?.token
    }

    fun clearToken() {
        val editor = prefs.edit()
        editor.remove("token")
        editor.remove("manterConectado")
        editor.remove("usuario")
        editor.apply()
    }
}
