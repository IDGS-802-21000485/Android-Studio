package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class EstadoProducModel(
    @SerializedName("idEstadoProduc") val idEstadoProduc: Int,
    @SerializedName("descripcion") val descripcion: String
)