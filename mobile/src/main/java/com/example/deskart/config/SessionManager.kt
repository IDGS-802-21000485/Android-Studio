package com.example.deskart.config

import android.content.Context
import com.example.deskart.models.UsuarioModel
import com.google.gson.Gson


class SessionManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("idgsSession", Context.MODE_PRIVATE)
    fun saveUser(user: UsuarioModel) {
        val json = Gson().toJson(user)
        sharedPreferences.edit().putString("user", json).apply()
        sharedPreferences.edit().putString("user_id", user.idUsuario.toString()).apply()
    }
    fun getUser(): UsuarioModel? {
        val json = sharedPreferences.getString("user", null)
        return Gson().fromJson(json, UsuarioModel::class.java)
    }
    fun getUserId(): String? {
        return sharedPreferences.getString("user_id", null)
    }
    fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }
}
