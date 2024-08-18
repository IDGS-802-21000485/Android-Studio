package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class ProveedorModel(
    @SerializedName("idProveedor") val idProveedor: Int,
    @SerializedName("empresa") val empresa: String,
    @SerializedName("nombrep") val nombreP: String,
    @SerializedName("telefono") val telefono: String,
    @SerializedName("correo") val correo: String
)