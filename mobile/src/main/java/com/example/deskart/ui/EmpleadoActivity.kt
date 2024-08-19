package com.example.deskart.ui

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
import com.example.deskart.models.UsuarioTiendaModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmpleadoActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleados)

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)

        RetrofitClient.instance.getUsuariosTienda().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val stringJson = response.body()?.string()
                    val gson = Gson()
                    val itemType = object : TypeToken<List<UsuarioTiendaModel>>() {}.type
                    val productos: List<UsuarioTiendaModel> = gson.fromJson(stringJson, itemType)

                    // Añadir los encabezados de la tabla
                    val headerRow = layoutInflater.inflate(R.layout.item_header, null)
                    tableLayout.addView(headerRow)

                    // Añadir los productos como filas de la tabla
                    for (producto in productos) {
                        val row = layoutInflater.inflate(R.layout.item_fila, null)
                        val nombre = row.findViewById<TextView>(R.id.tvNombre)
                        val puesto = row.findViewById<TextView>(R.id.tvPuesto)
                        val verDetalles = row.findViewById<Button>(R.id.btnVerDetalles)

                        var puestoS = ""
                        if (producto.rol == 2){
                            puestoS = "Produccion"
                        }

                        nombre.text = producto.nombre + " " + producto.primerApellido + " " + producto.segundoApellido
                        puesto.text = puestoS

                        // En EmpleadoActivity.kt
                        verDetalles.setOnClickListener {
                            val intent = Intent(this@EmpleadoActivity, DetallesEmpleadoActivity::class.java)
                            intent.putExtra("nombre", nombre.text)
                            intent.putExtra("puesto", puesto.text) // Asegúrate de pasar la descripción también
                            startActivity(intent)
                        }


                        tableLayout.addView(row)
                    }
                } else {
                    Toast.makeText(this@EmpleadoActivity, "Error al obtener empleados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@EmpleadoActivity, "Error de red: " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val role = sessionManager.getUserTien()?.rol// Recupera el rol del usuario
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
                Log.e("EmpleadoActivity", "Navegando a AdminActivity")
                return true
            }
            R.id.item2 -> {
                startActivity(Intent(this, EmpleadoActivity::class.java))
                Log.e("EmpleadoActivity", "Navegando a EmpleadoActivity")
                return true
            }
            R.id.logout -> {
                Toast.makeText(this, "Cerrando sesión", Toast.LENGTH_SHORT).show()
                Log.e("EmpleadoActivity", "Cerrando sesión")
                startActivity(Intent(this, MainActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
