package com.example.deskart.models

import com.google.gson.annotations.SerializedName

data class ProduccionModel(
    @SerializedName("idProduccion") val idProduccion: Int,
    @SerializedName("VentaTotal_idVentaTotal") val ventaTotalId: Int,
    @SerializedName("EstadoProduc_idEstadoProduc") val estadoProducId: Int
)