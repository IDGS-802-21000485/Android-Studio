package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class CarritoModel(
    @SerializedName("idCarrito") val idCarrito: Int,
    @SerializedName("productoIdProducto") val productoIdProducto: Int,
    @SerializedName("usuarioIdUsuario") val usuarioIdUsuario: Int
)
