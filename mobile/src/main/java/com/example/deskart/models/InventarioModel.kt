package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class InventarioModel(
    @SerializedName("idInventario") val idInventario: Int,
    @SerializedName("cantidad") val cantidad: String,
    @SerializedName("MateriaP_idMateriaP") val materiaPId: Int
)