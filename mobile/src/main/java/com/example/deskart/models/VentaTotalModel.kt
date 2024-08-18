package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class VentaTotalModel(
    @SerializedName("idVentaTotal") val idVentaTotal: Int,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("total") val total: Float,
    @SerializedName("VentaProd_idVentaProd") val ventaProdId: Int
)