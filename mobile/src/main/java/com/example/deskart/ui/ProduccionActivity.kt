package com.example.deskart.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deskart.MainActivity
import com.example.deskart.R
import com.example.deskart.apiservice.RetrofitClient
import com.example.deskart.config.SessionManager
import com.example.deskart.models.ProduccionModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProduccionActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produccion)

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)

        RetrofitClient.instance.getProduccion().enqueue(object : Callback<ResponseBody> {
            @SuppressLint("MissingInflatedId")
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val stringJson = response.body()?.string()
                    val gson = Gson()
                    val itemType = object : TypeToken<List<ProduccionModel>>() {}.type
                    val produccion: List<ProduccionModel> = gson.fromJson(stringJson, itemType)

                    // Añadir los encabezados de la tabla
                    val headerRow = layoutInflater.inflate(R.layout.item_header_produccion, null)
                    tableLayout.addView(headerRow)

                    // Añadir los produccion como filas de la tabla
                    for (producci in produccion) {
                        val row = layoutInflater.inflate(R.layout.item_fila_produccion, null)
                        val producto = row.findViewById<TextView>(R.id.tvProducto)
                        val cantidad = row.findViewById<TextView>(R.id.tvCantidad)
                        val estatus = row.findViewById<TextView>(R.id.tvEstatus)
                        val cambio = row.findViewById<Button>(R.id.btnEstatus)

                        Log.d("Producto", producci.receta.producto)

                        // Asignar valores a los TextViews
                        producto.text = producci.receta.producto
                        cantidad.text = producci.cantidad.toString()
                        estatus.text = producci.estado

                        cambio.setOnClickListener {
                            val intent = Intent(this@ProduccionActivity, DetallesProduccionActivity::class.java)
                            intent.putExtra("id", producci.idProduccion.toString())
                            intent.putExtra("estatus", producci.estado)
                            intent.putExtra("producto", producci.receta.producto)
                            intent.putExtra("cantidad", producci.cantidad.toString())
                            startActivity(intent)
                        }

                        tableLayout.addView(row)
                    }
                } else {
                    Toast.makeText(this@ProduccionActivity, "Error al obtener producción", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@ProduccionActivity, "Error de red: " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val role = sessionManager.getUserTien()?.rol // Recupera el rol del usuario
        if (role == 1) {
            menuInflater.inflate(R.menu.menu_admin, menu)
        } else {
            menuInflater.inflate(R.menu.menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item1 -> {
                startActivity(Intent(this, AdminActivity::class.java))
                Log.e("ProduccionActivity", "Navegando a AdminActivity")
                return true
            }
            R.id.item2 -> {
                startActivity(Intent(this, EmpleadoActivity::class.java))
                Log.e("EmpleadoActivity", "Navegando a ProduccionActivity")
                return true
            }
            R.id.item3 -> {
                startActivity(Intent(this, ProduccionActivity::class.java))
                Log.e("ProduccionActivity", "Navegando a ProduccionActivity")
                return true
            }
            R.id.logout -> {
                Toast.makeText(this, "Cerrando sesión", Toast.LENGTH_SHORT).show()
                Log.e("ProduccionActivity", "Cerrando sesión")
                startActivity(Intent(this, MainActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
