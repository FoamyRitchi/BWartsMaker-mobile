package com.example.bwartsmaker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "app.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "usuario"

        private const val COLUMN_CPF = "cpf_user"
        private const val COLUMN_NAME = "nome_user"
        private const val COLUMN_EMAIL = "email_user"
        private const val COLUMN_SENHA = "senha_user"
        private const val COLUMN_DDD = "ddd_user"
        private const val COLUMN_TELEFONE = "telefone_user"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val createTableQuery = """
            CREATE TABLE IF NOT EXISTS $TABLE_NAME (
                $COLUMN_CPF TEXT PRIMARY KEY,
                $COLUMN_NAME TEXT,
                $COLUMN_EMAIL TEXT,
                $COLUMN_SENHA TEXT,
                $COLUMN_DDD TEXT,
                $COLUMN_TELEFONE TEXT
            )
        """.trimIndent()

        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Atualizações futuras
    }

    fun insertUsuario(
        cpfUser: String,
        nomeUser: String,
        emailUser: String,
        senhaUser: String,
        dddUser: String,
        telefoneUser: String
    ) {

        val db = writableDatabase
        val values = ContentValues()

        values.put(COLUMN_CPF, cpfUser)
        values.put(COLUMN_NAME, nomeUser)
        values.put(COLUMN_EMAIL, emailUser)
        values.put(COLUMN_SENHA, senhaUser)
        values.put(COLUMN_DDD, dddUser)
        values.put(COLUMN_TELEFONE, telefoneUser)

        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    fun getAllUsuarios(): ArrayList<Usuario> {

        val usuarioList = ArrayList<Usuario>()

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME",
            null
        )

        if (cursor.moveToFirst()) {

            do {

                val cpfUser =
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CPF))

                val nomeUser =
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))

                val emailUser =
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))

                val senhaUser =
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SENHA))

                val dddUser =
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DDD))

                val telefoneUser =
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEFONE))

                usuarioList.add(
                    Usuario(
                        cpfUser,
                        nomeUser,
                        emailUser,
                        senhaUser,
                        dddUser,
                        telefoneUser
                    )
                )

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return usuarioList
    }

    fun updateUsuario(
        cpfUser: String,
        nomeUser: String,
        emailUser: String,
        senhaUser: String,
        dddUser: String,
        telefoneUser: String
    ) {

        val db = writableDatabase

        val values = ContentValues()

        values.put(COLUMN_NAME, nomeUser)
        values.put(COLUMN_EMAIL, emailUser)
        values.put(COLUMN_SENHA, senhaUser)
        values.put(COLUMN_DDD, dddUser)
        values.put(COLUMN_TELEFONE, telefoneUser)

        db.update(
            TABLE_NAME,
            values,
            "$COLUMN_CPF = ?",
            arrayOf(cpfUser)
        )

        db.close()
    }

    fun deleteUsuario(cpfUser : String): Int {
        val db = this.writableDatabase

        val linhasDeletadas = db.delete(
            TABLE_NAME,
            "$COLUMN_CPF = ?",
            arrayOf(cpfUser)
        )

        db.close()

        return linhasDeletadas
    }

        fun autenticarUsuario(cpfUser : String, senhaUser: String) : Boolean {
            val db = readableDatabase

            val cursor = db.rawQuery (
                """
                SELECT * FROM $TABLE_NAME
                WHERE $COLUMN_CPF = ?
                AND $COLUMN_SENHA = ?
                """.trimIndent(),
                arrayOf(cpfUser, senhaUser)
            )

            val usuarioExiste = cursor.count > 0

            cursor.close()
            db.close()

            return usuarioExiste

        }
}