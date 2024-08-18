package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class MermaModel(
    @SerializedName("idMerma") val idMerma: Int,
    @SerializedName("cantidad") val cantidad: Int,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("MateriaP_idMateriaP") val materiaPId: Int,
    @SerializedName("UsuarioTienda_idUsuarioTienda") val usuarioTiendaId: Int
)