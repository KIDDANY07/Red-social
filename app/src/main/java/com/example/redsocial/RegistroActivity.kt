// RegistroActivity.kt
package com.example.redsocial

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        //llama la clase DatabaseHelper para validar la base de datos etc
        dbHelper = DatabaseHelper(this)

        //Variables asignadas a los elementos de la interfaz
        val registerButton = findViewById<Button>(R.id.registrar)
        val nameEditText = findViewById<EditText>(R.id.nombre)
        val emailEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)

        registerButton.setOnClickListener {   //evento de boton registrar
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Se agrega el usuario a la base de datos
                val result = dbHelper.addUser(name, email, password)
                if (result != -1L) {
                    //si se logra el registro con exito
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    finish() // Se cierra la clase registro o mejor dicho la actividad
                } else {  //si el registro es fallido
                    Toast.makeText(this, "Registro fallido", Toast.LENGTH_SHORT).show()
                }
            } else { //si hay campos vacios
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        val backButton = findViewById<Button>(R.id.volver)
        backButton.setOnClickListener {
            finish() // Se cierra la clase registro o mejor dicho la actividad
        }
    }
}
