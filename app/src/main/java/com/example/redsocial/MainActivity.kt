// MainActivity.kt
package com.example.redsocial

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val registerButton = findViewById<Button>(R.id.registro)
        registerButton.setOnClickListener {
            // Busca en la actividad registro si hay algun dato
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
        //hace la lectura a la base de datos que se creo localmente en DatabaseHelper que es un clase
        dbHelper = DatabaseHelper(this)
        //Variables asignadas a los elementos de la interfaz
        val loginButton = findViewById<Button>(R.id.login)
        val emailEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (dbHelper.checkUser(email, password)) {
                    // Muestra el mensaje si el inicio de sesion es valido
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                } else {
                    // Muestra error si hay algun dato incorrecto
                    Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Da una advertencia en los campos vacios osea si no esta completo el formulario
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
