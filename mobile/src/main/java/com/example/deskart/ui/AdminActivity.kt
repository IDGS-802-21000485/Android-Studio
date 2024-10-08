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
import com.example.deskart.ui.Productos.TarjetaAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acticity_admin)
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
                startActivity(Intent(this, AdminActivity::class.java))
                Log.e("MainActivity", "Context: ${this}")
                return true
            }
            R.id.item2 -> {
                // Abrir InicioActivity
                startActivity(Intent(this, EmpleadoActivity::class.java))
                Log.e("MainActivity", "Context: ${this}")
                return true
            }
            R.id.item3-> {
                // Abrir InicioActivity
                startActivity(Intent(this, ProduccionActivity::class.java))
                Log.e("ProduccionActivity", "Context: ${this}")
                return true
            }
            R.id.logout -> {
                Toast.makeText(this, "Cerrando sesión", Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", "Context: ${this}")
                startActivity(Intent(this, MainActivity::class.java))
                return true

            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}