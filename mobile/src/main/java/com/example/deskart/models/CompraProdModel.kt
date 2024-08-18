package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class CompraProdModel(
    @SerializedName("idCompraProd") val idCompraProd: Int,
    @SerializedName("cantidad") val cantidad: Int,
    @SerializedName("subTotal") val subTotal: Int,
    @SerializedName("Proveedor_has_MateriaP_idProveedor_has_MateriaP") val proveedorHasMateriaPId: Int
)