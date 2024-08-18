package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class ProductoModel(
    @SerializedName("idProducto") val idProducto: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("alto") val alto: String,
    @SerializedName("largo") val largo: String,
    @SerializedName("ancho") val ancho: String,
    @SerializedName("precio") val precio: String,
    @SerializedName("imagen") val imagen: String
)