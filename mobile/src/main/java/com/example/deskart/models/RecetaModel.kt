package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class RecetaModel(
    @SerializedName("idReceta") val idReceta: Int,
    @SerializedName("cantidad") val cantidad: Int,
    @SerializedName("MateriaP_idMateriaP") val materiaPId: Int,
    @SerializedName("Producto_idProducto") val productoId: Int
)