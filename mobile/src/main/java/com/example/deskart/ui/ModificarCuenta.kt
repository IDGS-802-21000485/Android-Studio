package com.example.deskart.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.deskart.MainActivity
import com.example.deskart.R
import com.example.deskart.apiservice.RetrofitClient
import com.example.deskart.config.SessionManager
import com.example.deskart.models.modificarModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModificarCuenta : AppCompatActivity() {

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
    private lateinit var habilitarCheckBox: CheckBox
    private lateinit var registrarButton: Button
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_modificar_cuenta)
        sessionManager = SessionManager(this)

        // Inicialización de todas las vistas
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
        coloniaInputLayout = findViewById(R.id.coloniaInputLayout)
        coloniaInput = findViewById(R.id.coloniaEditText)
        cpInputLayout = findViewById(R.id.cpInputLayout)
        cpInput = findViewById(R.id.cpEditText)
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

        // Inicialización del CheckBox y Button
        habilitarCheckBox = findViewById(R.id.habilitarCheckBox)  // Asegúrate de que el ID es correcto
        registrarButton = findViewById(R.id.registrarButton)      // Asegúrate de que el ID es correcto

        // Configuración del Spinner
        val sexoOptions = arrayOf("Seleccionar sexo", "Hombre", "Mujer")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sexoOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sexoInput.adapter = adapter

        // Cargar los datos del usuario en los campos
        cargarDatosUsuario()

        setEditTextEnabled(false)

        // Configura el CheckBox listener
        habilitarCheckBox.setOnCheckedChangeListener { _, isChecked ->
            setEditTextEnabled(isChecked)
        }

        // Configuración del Button listener
        val id = sessionManager.getUser()?.idUsuario.toString() // Suponiendo que el ID es un string
        registrarButton.setOnClickListener {
            if (habilitarCheckBox.isChecked) {
                // Crear objeto modificarModel con los datos modificados
                val params = modificarModel(
                    id,
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
                    sexoInput.selectedItem.toString()
                )
                Log.d("DatosModificarCuenta", params.toString())
                actualizarUsuario(params)
            } else {
                Toast.makeText(this, "Habilita la edición para guardar cambios", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarDatosUsuario() {
        val usuario = sessionManager.getUser()

        // Verificar si el usuario no es nulo
        usuario?.let {
            nombreInput.setText(it.nombre)
            primerApellidoInput.setText(it.primerApellido)
            segundoApellidoInput.setText(it.segundoApellido)
            nombreUsuarioInput.setText(it.nombreUsuario)
            contrasenaInput.setText(it.contrasenia)
            cpInput.setText(it.cp)
            coloniaInput.setText(it.colonia)
            calleInput.setText(it.calle)
            numExInput.setText(it.numEx)
            telefonoInput.setText(it.telefono)
            emailInput.setText(it.email)

            // Establecer el valor seleccionado en el Spinner
            val sexoIndex = when (it.sexo) {
                "Hombre" -> 1
                "Mujer" -> 2
                else -> 0
            }
            sexoInput.setSelection(sexoIndex)
        }
    }

    private fun setEditTextEnabled(enabled: Boolean) {
        nombreInput.isEnabled = enabled
        primerApellidoInput.isEnabled = enabled
        segundoApellidoInput.isEnabled = enabled
        nombreUsuarioInput.isEnabled = enabled
        contrasenaInput.isEnabled = enabled
        cpInput.isEnabled = enabled
        coloniaInput.isEnabled = enabled
        calleInput.isEnabled = enabled
        numExInput.isEnabled = enabled
        telefonoInput.isEnabled = enabled
        emailInput.isEnabled = enabled
        sexoInput.isEnabled = enabled // Si quieres que el Spinner también sea habilitable
    }

    private fun actualizarUsuario(parametros: modificarModel) {
        RetrofitClient.instance.putActualizar(parametros)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ModificarCuenta, "Usuario actualizado exitosamente", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@ModificarCuenta, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@ModificarCuenta, "Error al actualizar el usuario", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("ModificarCuenta", "Error: ${t.message}")
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
                startActivity(Intent(this, Productos::class.java))
                return true
            }
            R.id.item2 -> {
                startActivity(Intent(this, ModificarCuenta::class.java))
                return true
            }
            R.id.item3 -> {
                startActivity(Intent(this, MainActivity::class.java))
                return true
            }
            R.id.logout -> {
                Toast.makeText(this, "Cerrando sesión", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
