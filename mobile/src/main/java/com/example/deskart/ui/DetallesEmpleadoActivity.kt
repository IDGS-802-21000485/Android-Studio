package com.example.deskart.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.deskart.R

class DetallesEmpleadoActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_empleado)

        val nombreTextView = findViewById<TextView>(R.id.tvNombre)
        val puestoTextView = findViewById<TextView>(R.id.tvPuesto)

        // Obtener los extras pasados desde la actividad anterior
        val nombre = intent.getStringExtra("nombre")
        val puesto = intent.getStringExtra("puesto")

        Log.d("DetallesEmpleadoActivity", "Nombre recibido: $nombre")
        Log.d("DetallesEmpleadoActivity", "Descripción recibida: $puesto")

        // Verificar si los valores no son nulos antes de asignarlos a los TextViews
        if (nombre != null) {
            nombreTextView.text = nombre
            puestoTextView.text = puesto
        } else {
            nombreTextView.text = "Nombre no disponible"
            puestoTextView.text = "Descripción no disponible"
        }
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)
        btnRegresar.setOnClickListener {
            finish()
        }
    }
}
