package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class UsuarioModel(
    @SerializedName("idUsuario") val idUsuario: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("primerApellido") val primerApellido: String,
    @SerializedName("segundoApellido") val segundoApellido: String,
    @SerializedName("nombreUsuario") val nombreUsuario: String,
    @SerializedName("contrasenia") val contrasenia: String,
    @SerializedName("colonia") val colonia: String,
    @SerializedName("calle") val calle: String,
    @SerializedName("cp") val cp: String,
    @SerializedName("num_ex") val numEx: String,
    @SerializedName("telefono") val telefono: String,
    @SerializedName("email") val email: String,
    @SerializedName("sexo") val sexo: String
)