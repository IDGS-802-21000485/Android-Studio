package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class UsuarioTiendaModel(
    @SerializedName("idUsuarioTienda") val idUsuarioTienda: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("primerApellido") val primerApellido: String,
    @SerializedName("segundoApellido") val segundoApellido: String,
    @SerializedName("nombreUsuario") val nombreUsuario: String,
    @SerializedName("contrasenia") val contrasenia: String,
    @SerializedName("telefono") val telefono: String,
    @SerializedName("email") val email: String,
    @SerializedName("cp") val cp: String,
    @SerializedName("rol") val rol: Int,
    @SerializedName("estatus") val estatus: Int,
    @SerializedName("sexo") val sexo: String
)