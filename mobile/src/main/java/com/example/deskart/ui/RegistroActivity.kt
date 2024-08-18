package com.example.deskart.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deskart.MainActivity
import com.example.deskart.R
import com.example.deskart.apiservice.RetrofitClient
import com.example.deskart.models.RegistroModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroActivity : AppCompatActivity() {
    lateinit var nombreInputLayout: TextInputLayout
    lateinit var nombreInput: TextInputEditText
    lateinit var primerApellidoInputLayout: TextInputLayout
    lateinit var primerApellidoInput: TextInputEditText
    lateinit var segundoApellidoInputLayout: TextInputLayout
    lateinit var segundoApellidoInput: TextInputEditText
    lateinit var nombreUsuarioInputLayout: TextInputLayout
    lateinit var nombreUsuarioInput: TextInputEditText
    lateinit var contrasenaInputLayout: TextInputLayout
    lateinit var contrasenaInput: TextInputEditText
    lateinit var coloniaInputLayout: TextInputLayout
    lateinit var coloniaInput: TextInputEditText
    lateinit var cpInputLayout: TextInputLayout
    lateinit var cpInput: TextInputEditText
    lateinit var calleInputLayout: TextInputLayout
    lateinit var calleInput: TextInputEditText
    lateinit var numExInputLayout: TextInputLayout
    lateinit var numExInput: TextInputEditText
    lateinit var telefonoInputLayout: TextInputLayout
    lateinit var telefonoInput: TextInputEditText
    lateinit var emailInputLayout: TextInputLayout
    lateinit var emailInput: TextInputEditText
    lateinit var sexoInputLayout: TextInputLayout
    lateinit var sexoInput: Spinner
    lateinit var contexto: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contexto = this
        setContentView(R.layout.activity_registro)

        nombreInputLayout = findViewById(R.id.nombreInputLayout)
        nombreInput = findViewById(R.id.nombreEditText)
        primerApellidoInputLayout = findViewById(R.id.primerApellidoInputLayout)
        primerApellidoInput = findViewById(R.id.primerApellidoEditText)
        segundoApellidoInputLayout = findViewById(R.id.segundoApellidoInputLayout)
        segundoApellidoInput = findViewById(R.id.segundoApellidoEditText)
        nombreUsuarioInputLayout = findViewById(R.id.nombreUsuarioInputLayout)
        nombreUsuarioInput = findViewById(R.id.nombreUsuarioEditText)
        contrasenaInputLayout = findViewById(R.id.contrasenaInputLayout)
        contrasenaInput = findViewById(R.id.contrasenaEditText)
        coloniaInputLayout = findViewById(R.id.cpInputLayout)
        cpInput = findViewById(R.id.cpEditText)
        cpInputLayout = findViewById(R.id.coloniaInputLayout)
        coloniaInput = findViewById(R.id.coloniaEditText)
        calleInputLayout = findViewById(R.id.calleInputLayout)
        calleInput = findViewById(R.id.calleEditText)
        numExInputLayout = findViewById(R.id.numExInputLayout)
        numExInput = findViewById(R.id.numExEditText)
        telefonoInputLayout = findViewById(R.id.telefonoInputLayout)
        telefonoInput = findViewById(R.id.telefonoEditText)
        emailInputLayout = findViewById(R.id.emailInputLayout)
        emailInput = findViewById(R.id.emailEditText)
        sexoInputLayout = findViewById(R.id.sexoInputLayout)
        sexoInput = findViewById(R.id.sexoInput)

        val sexoOptions = arrayOf("Seleccionar sexo", "Hombre", "Mujer")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sexoOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sexoInput.adapter = adapter

        fun registrarme(parametros: RegistroModel) {
            RetrofitClient.instance.postRegistrar(parametros)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@RegistroActivity,
                                "Registro exitoso",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@RegistroActivity, MainActivity::class.java)
                            intent.putExtra("email", emailInput.text.toString())
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                contexto,
                                "${response.errorBody()?.string()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e("RegistroActivity", "Failure: ${t.message}")
                    }
                })
        }

        fun formValido(): Boolean {
            var valido = true
            if (nombreInput.text.toString().isEmpty()) {
                nombreInputLayout.error = "Campo obligatorio"
                valido = false
            } else {
                nombreInputLayout.error = ""
            }
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
            if (coloniaInput.text.toString().isEmpty()) {
                coloniaInputLayout.error = "Campo obligatorio"
                valido = false
            } else {
                coloniaInputLayout.error = ""
            }
            if (cpInput.text.toString().isEmpty()) {
                cpInputLayout.error = "Campo obligatorio"
                valido = false
            } else {
                cpInputLayout.error = ""
            }
            if (calleInput.text.toString().isEmpty()) {
                calleInputLayout.error = "Campo obligatorio"
                valido = false
            } else {
                calleInputLayout.error = ""
            }
            if (numExInput.text.toString().isEmpty()) {
                numExInputLayout.error = "Campo obligatorio"
                valido = false
            } else {
                numExInputLayout.error = ""
            }
            if (telefonoInput.text.toString().isEmpty()) {
                telefonoInputLayout.error = "Campo obligatorio"
                valido = false
            } else {
                telefonoInputLayout.error = ""
            }

            val valorIndice = sexoInput.selectedItemPosition
            if (valorIndice == 0) {
                sexoInputLayout.error = "Indica tu sexo"
                valido = false
            } else {
                sexoInputLayout.error = ""
            }
            return valido
        }

        val registrarButton = findViewById<Button>(R.id.registrarButton)
        registrarButton.setOnClickListener {
            if (formValido()) {
                val params = RegistroModel(
                    nombreInput.text.toString(),
                    primerApellidoInput.text.toString(),
                    segundoApellidoInput.text.toString(),
                    nombreUsuarioInput.text.toString(),
                    contrasenaInput.text.toString(),
                    cpInput.text.toString(),
                    coloniaInput.text.toString(),
                    calleInput.text.toString(),
                    numExInput.text.toString(),
                    telefonoInput.text.toString(),
                    emailInput.text.toString(),
                    sexoInput.selectedItemPosition.toString()
                )
                registrarme(params)
            } else {
                Toast.makeText(this, "Existe información incorrecta", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
