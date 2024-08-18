package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class VentaProdModel(
    @SerializedName("idVentaProd") val idVentaProd: Int,
    @SerializedName("cantidad") val cantidad: Int,
    @SerializedName("subTotal") val subTotal: Float,
    @SerializedName("UsuarioTienda_idUsuarioTienda1") val usuarioTiendaId1: Int,
    @SerializedName("Usuario_idUsuario") val usuarioId: Int,
    @SerializedName("Producto_idProducto") val productoId: Int
)