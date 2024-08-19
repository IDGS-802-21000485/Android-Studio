package com.example.deskart.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deskart.MainActivity
import com.example.deskart.R
import com.example.deskart.apiservice.RetrofitClient
import com.example.deskart.config.SessionManager
import com.example.deskart.models.ProductoModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Productos : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        RetrofitClient.instance.getProductos().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val stringJson = response.body()?.string()
                    val gson = Gson()
                    val itemType = object : TypeToken<List<ProductoModel>>() {}.type
                    val productos: List<ProductoModel> = gson.fromJson(stringJson, itemType)

                    // Actualizar el RecyclerView con los datos obtenidos
                    val adapter = TarjetaAdapter(productos)
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@Productos, "Error al obtener productos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@Productos, "Error de red: " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    class TarjetaAdapter(private val listaTarjetas: List<ProductoModel>) :
        RecyclerView.Adapter<TarjetaAdapter.TarjetaViewHolder>() {

        class TarjetaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titulo: TextView = itemView.findViewById(R.id.tvTitulo)
            val descripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
            val btnVerDetalles: Button = itemView.findViewById(R.id.btnVerDetalles)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarjetaViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarjeta, parent, false)
            return TarjetaViewHolder(view)
        }

        override fun onBindViewHolder(holder: TarjetaViewHolder, position: Int) {
            val tarjeta = listaTarjetas[position]
            holder.titulo.text = tarjeta.nombre
            holder.descripcion.text = tarjeta.descripcion

            holder.btnVerDetalles.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, DetallesProductoActivity::class.java)
                intent.putExtra("nombre", tarjeta.nombre)
                intent.putExtra("descripcion", tarjeta.descripcion)
                intent.putExtra("medidas", tarjeta.alto)
                intent.putExtra("idProducto", tarjeta.idProducto)
                // Añade más extras según sea necesario
                context.startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return listaTarjetas.size
        }
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
                // Abrir InicioActivity
                startActivity(Intent(this, Productos::class.java))
                Log.e("MainActivity", "Context: ${this}")
                return true
            }
            R.id.item2 -> {
                // Abrir ComprasActivity
                startActivity(Intent(this, ModificarCuenta::class.java))

                Log.e("CarritoActivity", "Context: ${this}")
                return true
            }
            R.id.item3 -> {
                // Abrir CarritoActivity
                startActivity(Intent(this, MainActivity::class.java))
                Log.e("MainActivity", "Context: ${this}")
                return true
            }
            R.id.logout -> {
                // Abrir CarritoActivity


                Toast.makeText(this, "Cerrando sesión", Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", "Context: ${this}")
                return true

            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
