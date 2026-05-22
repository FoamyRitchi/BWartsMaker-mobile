package com.example.bwartsmaker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class CadastroActivity : AppCompatActivity() {


    private lateinit var databaseHelper: DatabaseHelper

    private lateinit var erro : TextView

    private lateinit var nome : EditText
    private lateinit  var email : EditText
    private lateinit var cpf : EditText
    private lateinit var ddd : EditText
    private lateinit var telefone : EditText
    private lateinit var senha : EditText
    private lateinit var linkLogin : TextView

    private lateinit var botaoCadastro : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.cadastro)


        databaseHelper = DatabaseHelper(this)

        nome = findViewById(R.id.nome_cadastro)
        email = findViewById(R.id.email_cadastro)
        cpf = findViewById(R.id.cpf_cadastro)
        ddd = findViewById(R.id.ddd_cadastro)
        telefone = findViewById(R.id.telefone_cadastro)
        senha = findViewById(R.id.senha_cadastro)
        erro = findViewById(R.id.erro)
        botaoCadastro = findViewById(R.id.botao_cadastrar)

        linkLogin = findViewById(R.id.facaLoginCadastro)

        botaoCadastro.setOnClickListener {
            try {
                databaseHelper.insertUsuario(cpf.text.toString(), nome.text.toString(), email.text.toString(), senha.text.toString(), ddd.text.toString(), telefone.text.toString())
                erro.text = "sucesso"
            } catch (e: Exception) {
                erro.text = e.toString()
            }
        }




        linkLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}