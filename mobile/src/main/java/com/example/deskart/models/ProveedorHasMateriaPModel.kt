package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class ProveedorHasMateriaPModel(
    @SerializedName("idProveedor_has_MateriaP") val idProveedorHasMateriaP: Int,
    @SerializedName("MateriaP_idMateriaP1") val materiaPId1: Int,
    @SerializedName("Proveedor_idProveedor1") val proveedorId1: Int
)