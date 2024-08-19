package com.example.deskart.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deskart.R
import com.example.deskart.apiservice.AuthApiService
import com.example.deskart.apiservice.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetallesProduccionActivity : AppCompatActivity() {

    private lateinit var apiService: AuthApiService

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_produccion)

        val nombreTextView = findViewById<TextView>(R.id.tvNombre)
        val cantidadTextView = findViewById<TextView>(R.id.tvPuesto)
        val estatusTextView = findViewById<TextView>(R.id.tvEstatus)
        val btnCambiarEstatus = findViewById<Button>(R.id.btnCambiarEstatus)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        val id = intent.getStringExtra("id")
        val estatus = intent.getStringExtra("estatus")
        val nombre = intent.getStringExtra("producto")
        val cantidad = intent.getStringExtra("cantidad")

        Log.d("DetallesProduccionActivity", "ID recibido: $id")
        Log.d("DetallesProduccionActivity", "Estatus recibido: $estatus")

        nombreTextView.text = nombre ?: "Nombre no disponible"
        cantidadTextView.text = cantidad ?: "Cantidad no disponible"
        estatusTextView.text = cantidad ?: "Estatus no disponible"

        btnRegresar.setOnClickListener {
            finish()
        }

        // Configurar el botón de cancelar
        btnCancelar.setOnClickListener {
            if (estatus == "Produciendo") {
                Toast.makeText(this, "No se puede cancelar, el estado es Produciendo.", Toast.LENGTH_SHORT).show()
            } else {
                RetrofitClient.instance.putCancelarProduccion(id.toString()).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        // Ignorar el código de estado y asumir que la acción fue exitosa
                        Toast.makeText(this@DetallesProduccionActivity, "Producción cancelada exitosamente.", Toast.LENGTH_SHORT).show()

                        // Recargar ProduccionActivity para reflejar los cambios
                        val intent = Intent(this@DetallesProduccionActivity, ProduccionActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish() // Finalizar la actividad actual
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@DetallesProduccionActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }


        // Configurar el botón de cambiar estatus
        btnCambiarEstatus.setOnClickListener {
            when (estatus) {
                "Pendiente" -> {
                    RetrofitClient.instance.putIniciarProduccion(id.toString()).enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@DetallesProduccionActivity, "Producción iniciada exitosamente.", Toast.LENGTH_SHORT).show()
                                // Recargar ProduccionActivity
                                val intent = Intent(this@DetallesProduccionActivity, ProduccionActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@DetallesProduccionActivity, "Error al iniciar producción.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(this@DetallesProduccionActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                "Produciendo" -> {
                    RetrofitClient.instance.putCompletarProduccion(id.toString()).enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@DetallesProduccionActivity, "Producción completada exitosamente.", Toast.LENGTH_SHORT).show()
                                // Recargar ProduccionActivity
                                val intent = Intent(this@DetallesProduccionActivity, ProduccionActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@DetallesProduccionActivity, "Error al completar producción.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(this@DetallesProduccionActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                else -> {
                    Toast.makeText(this, "Estatus no válido para cambiar.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}
