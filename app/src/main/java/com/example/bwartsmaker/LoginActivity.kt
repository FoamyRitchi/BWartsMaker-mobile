package com.example.bwartsmaker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    private lateinit var linkCadastro: TextView

    private lateinit var cpf: EditText
    private lateinit var senha: EditText

    private lateinit var erro: TextView

    private lateinit var botaoLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.login)

        databaseHelper = DatabaseHelper(this)

        cpf = findViewById(R.id.userLogin)
        senha = findViewById(R.id.senhaLogin)

        erro = findViewById(R.id.textView8)

        botaoLogin = findViewById(R.id.botaoEntrar)

        botaoLogin.setOnClickListener {

            val cpfTexto = cpf.text.toString()
            val senhaTexto = senha.text.toString()

            val autenticado =
                databaseHelper.autenticarUsuario(cpfTexto, senhaTexto)

            if (autenticado) {

                Toast.makeText(
                    this,
                    "Login realizado!",
                    Toast.LENGTH_SHORT
                ).show()

                erro.text = "Sucesso"

                // val intent =
                //    Intent(this, PerfilActivity::class.java)

                // startActivity(intent)

            } else {

                Toast.makeText(
                    this,
                    "CPF ou senha inválidos",
                    Toast.LENGTH_SHORT
                ).show()

                erro.text = "Erro"

            }
        }

        linkCadastro = findViewById(R.id.cadastreLogin)

        linkCadastro.setOnClickListener {

            val intent =
                Intent(this, CadastroActivity::class.java)

            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }
    }
}