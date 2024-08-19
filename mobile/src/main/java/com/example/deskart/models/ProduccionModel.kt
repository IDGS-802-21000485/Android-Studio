package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class ProduccionModel(
    @SerializedName("idProduccion") val idProduccion: Int,
    @SerializedName("estado") val estado: String,
    @SerializedName("receta") val receta: RecetaModel,
    @SerializedName("cantidad") val cantidad: Int,
    @SerializedName("fechaRegistro") val fechaRegistro: String
)

data class RecetaModel(
    @SerializedName("idReceta") val idReceta: Int,
    @SerializedName("producto") val producto: String,
    @SerializedName("descripcion") val descripcion: String
)
