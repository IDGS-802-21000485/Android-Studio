package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class MateriaPModel(
    @SerializedName("idMateriaP") val idMateriaP: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("medida") val medida: String
)