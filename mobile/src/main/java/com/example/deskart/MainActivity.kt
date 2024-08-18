package com.example.deskart

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deskart.models.LoginModel
import com.example.deskart.ui.RegistroActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.example.deskart.apiservice.RetrofitClient
import com.example.deskart.models.UsuarioModel
import com.example.deskart.ui.ModificarCuenta
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.deskart.config.SessionManager
import com.example.deskart.ui.Productos
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.deskart.ui.AdminActivity

class MainActivity : AppCompatActivity() {
    lateinit var contexto: Context
    lateinit var emailInputLayout: TextInputLayout
    lateinit var emailInput: TextInputEditText
    lateinit var contrasenaInputLayout: TextInputLayout
    lateinit var contrasenaInput: TextInputEditText
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contexto = this
        sessionManager = SessionManager(this)
        contrasenaInputLayout = findViewById<TextInputLayout>(R.id.contrasenaInputLayout)
        contrasenaInput = findViewById<TextInputEditText>(R.id.contrasenaEditText)
        emailInputLayout = findViewById<TextInputLayout>(R.id.emailInputLayout)
        emailInput = findViewById<TextInputEditText>(R.id.emailEditText)

        val emailParam = intent.getStringExtra("email")
        if (emailParam != null) {
            emailInput.setText(emailParam.toString())
        }

        val loginButton = findViewById<Button>(R.id.btnIniciarSesion)
        loginButton.setOnClickListener {
            if (formValido()) {
                val params = LoginModel(emailInput.text.toString(), contrasenaInput.text.toString())
                login(params)
            } else {
                Toast.makeText(this, "Existe información incorrecta", Toast.LENGTH_SHORT).show()
            }
        }

        val textViewCrearCuenta: TextView = findViewById(R.id.textViewCrearCuenta)
        textViewCrearCuenta.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        sendNotification(contexto)
    }

    fun login(parametros: LoginModel) {
        Toast.makeText(this, "Iniciando sesión", Toast.LENGTH_SHORT).show()

        // Primera API
        RetrofitClient.instance.postLogin(parametros).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Si el usuario es encontrado en la primera API
                    val stringJson = response.body()?.string()
                    val gson = Gson()
                    val usuario = gson.fromJson(stringJson, UsuarioModel::class.java)
                    sessionManager.saveUser(usuario)
                    Toast.makeText(contexto, "Bienvenido ${usuario.nombre}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(contexto, Productos::class.java)
                    startActivity(intent)
                } else {
                    // Si el usuario no es encontrado, intenta con la segunda API
                    buscarEnSegundaApi(parametros)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // En caso de error en la primera API, intenta con la segunda API
                Log.e("MainActivity", "Error en la primera API: ${t.message}")
                buscarEnSegundaApi(parametros)
            }
        })
    }

    fun buscarEnSegundaApi(parametros: LoginModel) {
        // Segunda API
        RetrofitClient.instance.postLoginTienda(parametros).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Si el usuario es encontrado en la segunda API
                    val stringJson = response.body()?.string()
                    val gson = Gson()
                    val usuario = gson.fromJson(stringJson, UsuarioModel::class.java)
                    sessionManager.saveUser(usuario)
                    Toast.makeText(contexto, "Bienvenido ${usuario.nombre}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(contexto, AdminActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(contexto, "Usuario no encontrado en ninguna API", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // En caso de error en la segunda API
                Log.e("MainActivity", "Error en la segunda API: ${t.message}")
                Toast.makeText(contexto, "Error al conectar con la API", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun formValido(): Boolean {
        var valido = true
        if (contrasenaInput.text.toString().isEmpty()) {
            contrasenaInputLayout.error = "Campo obligatorio"
            valido = false
        } else {
            contrasenaInputLayout.error = ""
        }
        if (emailInput.text.toString().isEmpty()) {
            emailInputLayout.error = "Campo obligatorio"
            valido = false
        } else {
            emailInputLayout.error = ""
        }
        return valido
    }

    fun sendNotification(context: Context) {
        // Crear un Intent para abrir una actividad específica
        val intent = Intent(context, MainActivity::class.java).apply {
            // Puedes agregar datos adicionales al Intent si es necesario
        }

        // Especificar FLAG_IMMUTABLE si el PendingIntent no necesita ser modificado
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Crear un canal de notificación para Android 8.0 (Oreo) y superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Canal de Notificación"
            val descriptionText = "Descripción del canal"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("my_channel_id", name, importance).apply {
                description = descriptionText
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Construir la notificación
        val builder = NotificationCompat.Builder(context, "my_channel_id")
            .setSmallIcon(R.drawable.deskart_logo)
            .setContentTitle("Notificación")
            .setContentText("Este es un mensaje para Wear OS")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Enviar la notificación
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        notificationManager.notify(1, builder.build())
    }
}
