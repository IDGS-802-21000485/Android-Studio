package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class CompraTotalModel(
    @SerializedName("idCompra") val idCompra: Int,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("total") val total: Float,
    @SerializedName("CompraProd_idCompraProd") val compraProdId: Int
)