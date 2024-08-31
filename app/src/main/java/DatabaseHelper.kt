// DatabaseHelper.kt
package com.example.redsocial

import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {  //esta fue una prueba con SQLite
        private const val DATABASE_NAME = "redsocial.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USER = "usuario"
        private const val COLUMN_ID = "ID_USU"
        private const val COLUMN_NAME = "NOMBRE_USU"
        private const val COLUMN_EMAIL = "CORREO_USU"
        private const val COLUMN_PASSWORD = "CONTRASENA_USU"
    }
    //Aqui creamos una base de datos de forma local
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableUser = """
            CREATE TABLE $TABLE_USER (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL UNIQUE,
                $COLUMN_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()

        db?.execSQL(createTableUser)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Esta es una funcion por si antes de generar el codigo o lanzar la app no existe la base de datos
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    // Esta es una funcion para saber si existe el usuario
    fun checkUser(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USER WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?", arrayOf(email, password))
        val userExists = cursor.count > 0
        cursor.close()
        return userExists
    }

    // Esta es la funcion para agregar usuarios
    fun addUser(name: String, email: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
        }
        return db.insert(TABLE_USER, null, values)
    }
}
