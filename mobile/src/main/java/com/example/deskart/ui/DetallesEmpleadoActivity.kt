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

        // Obtener referencias a los TextViews
        val nombreTextView = findViewById<TextView>(R.id.tvNombre)
        val puestoTextView = findViewById<TextView>(R.id.tvPuesto)
        val usuarioTextView = findViewById<TextView>(R.id.tvUsuario)
        val telefonoTextView = findViewById<TextView>(R.id.tvTelefono)
        val correoTextView = findViewById<TextView>(R.id.tvCorreo)
        val generoTextView = findViewById<TextView>(R.id.tvGenero)

        // Obtener los extras pasados desde la actividad anterior
        val nombre = intent.getStringExtra("nombre")
        val puesto = intent.getStringExtra("puesto")
        val usuario = intent.getStringExtra("usuario")
        val telefono = intent.getStringExtra("telefono")
        val correo = intent.getStringExtra("correo")
        val genero = intent.getStringExtra("genero")

        // Logs para depuración
        Log.d("DetallesEmpleadoActivity", "Nombre recibido: $nombre")
        Log.d("DetallesEmpleadoActivity", "Puesto recibido: $puesto")
        Log.d("DetallesEmpleadoActivity", "Usuario recibido: $usuario")
        Log.d("DetallesEmpleadoActivity", "Teléfono recibido: $telefono")
        Log.d("DetallesEmpleadoActivity", "Correo recibido: $correo")
        Log.d("DetallesEmpleadoActivity", "Género recibido: $genero")

        // Asignar los valores a los TextViews
        nombreTextView.text = nombre ?: "Nombre no disponible"
        puestoTextView.text = puesto ?: "Puesto no disponible"
        usuarioTextView.text = usuario ?: "Usuario no disponible"
        telefonoTextView.text = telefono ?: "Teléfono no disponible"
        correoTextView.text = correo ?: "Correo no disponible"
        generoTextView.text = genero ?: "Género no disponible"

        // Configurar el botón "Regresar"
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)
        btnRegresar.setOnClickListener {
            finish()
        }
    }
}
