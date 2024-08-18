package com.example.deskart.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deskart.R
import com.example.deskart.apiservice.RetrofitClient
import com.example.deskart.config.SessionManager
import com.example.deskart.models.CarritoModel
import com.example.deskart.models.ProductoModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetallesProductoActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_producto)
        sessionManager = SessionManager(this)

        val nombre = intent.getStringExtra("nombre")
        val descripcion = intent.getStringExtra("descripcion")
        val medidas = intent.getStringExtra("medidas")
        val idProducto = intent.getIntExtra("idProducto", -1)  // Aquí se obtiene como Int

        if (idProducto == -1) {
            Toast.makeText(this, "ID de producto no válido", Toast.LENGTH_SHORT).show()
            finish() // Finaliza la actividad si el ID no es válido
            return
        }

        val tvNombre = findViewById<TextView>(R.id.tvNombre)
        val tvDescripcion = findViewById<TextView>(R.id.tvDescripcion)
        val tvMedidas = findViewById<TextView>(R.id.tvMedidas)

        tvNombre.text = nombre
        tvDescripcion.text = descripcion
        tvMedidas.text = medidas

        val btnRegresar = findViewById<Button>(R.id.btnRegresar)
        btnRegresar.setOnClickListener {
            finish()
        }

        val registrarButton = findViewById<Button>(R.id.btnAgregar)
        val userId = sessionManager.getUser()?.idUsuario ?: return
        val idUs = userId.toInt()


        registrarButton.setOnClickListener {
            val params = CarritoModel(0, idProducto, idUs)  // idProducto se pasa como Int
            Log.d("params", params.toString())
            agregarCarrito(params)
        }
    }

    private fun agregarCarrito(params: CarritoModel) {
        RetrofitClient.instance.postCarrito(params)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.d("DatosModificarCuenta", response.isSuccessful.toString())
                    if (response.isSuccessful) {
                        Toast.makeText(this@DetallesProductoActivity, "Agregado exitosamente", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@DetallesProductoActivity, Productos::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@DetallesProductoActivity, "Error al agregar al carrito", Toast.LENGTH_SHORT).show()
                        val errorBody = response.errorBody()?.string()
                        Log.e("Error", "Error en la respuesta: $errorBody")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("ErrorDP", "Error: ${t.message}")
                }
            })
    }
}
