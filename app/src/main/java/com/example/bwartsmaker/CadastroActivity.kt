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

            val nomeTexto = nome.text.toString().trim()
            val emailTexto = email.text.toString().trim()
            val cpfTexto = cpf.text.toString().trim()
            val dddTexto = ddd.text.toString().trim()
            val telefoneTexto = telefone.text.toString().trim()
            val senhaTexto = senha.text.toString().trim()

            var valido = true

            // Nome
            if (nomeTexto.isEmpty()) {

                nome.error = "Digite seu nome"
                valido = false
            }

            // Email
            if (emailTexto.isEmpty()) {

                email.error = "Digite seu email"
                valido = false

            } else if (
                !android.util.Patterns.EMAIL_ADDRESS
                    .matcher(emailTexto)
                    .matches()
            ) {

                email.error = "Email inválido"
                valido = false
            }

            // CPF
            if (cpfTexto.isEmpty()) {

                cpf.error = "Digite o CPF"
                valido = false

            } else if (cpfTexto.length != 11) {

                cpf.error = "CPF deve ter 11 números"
                valido = false
            }

            // DDD
            if (dddTexto.length != 2) {

                ddd.error = "DDD inválido"
                valido = false
            }

            // Telefone
            if (telefoneTexto.length < 8) {

                telefone.error = "Telefone inválido"
                valido = false
            }

            // Senha
            if (senhaTexto.isEmpty()) {

                senha.error = "Digite a senha"
                valido = false

            } else if (senhaTexto.length < 6) {

                senha.error = "Senha muito curta"
                valido = false
            }

            // Verifica se CPF já existe
            if (databaseHelper.cpfExiste(cpfTexto)) {

                cpf.error = "CPF já cadastrado"
                valido = false
            }

            // Cadastro
            if (valido) {

                try {

                    databaseHelper.insertUsuario(
                        cpfTexto,
                        nomeTexto,
                        emailTexto,
                        senhaTexto,
                        dddTexto,
                        telefoneTexto
                    )

                    erro.text = "Cadastro realizado com sucesso"

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                } catch (e: Exception) {

                    erro.text = e.toString()
                }
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