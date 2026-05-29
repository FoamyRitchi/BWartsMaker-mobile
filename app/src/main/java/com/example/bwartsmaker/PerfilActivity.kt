package com.example.bwartsmaker

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PerfilActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper

    private var modoEdicao = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)

        db = DatabaseHelper(this)

        val nomePerfil = findViewById<EditText>(R.id.nome_perfil)
        val cpfPerfil = findViewById<EditText>(R.id.cpf_perfil)
        val emailPerfil = findViewById<EditText>(R.id.email_perfil)
        val senhaPerfil = findViewById<EditText>(R.id.senha_perfil)
        val dddPerfil = findViewById<EditText>(R.id.ddd_perfil)
        val telefonePerfil = findViewById<EditText>(R.id.telefone_perfil)

        val editarPerfil = findViewById<Button>(R.id.editar_perfil)
        val excluirPerfil = findViewById<Button>(R.id.excluir_perfil)

        val cpf = intent.getStringExtra("CPF_USUARIO")

        if (cpf != null) {

            val usuario = db.getUsuarioByCpf(cpf)

            if (usuario != null) {

                nomePerfil.setText(usuario.nomeUser)
                cpfPerfil.setText(usuario.cpfUser)
                emailPerfil.setText(usuario.emailUser)
                senhaPerfil.setText(usuario.senhaUser)
                dddPerfil.setText(usuario.dddUser)
                telefonePerfil.setText(usuario.telefoneUser)
            }
        }

        editarPerfil.setOnClickListener {

            if (!modoEdicao) {

                modoEdicao = true

                nomePerfil.isEnabled = true
                emailPerfil.isEnabled = true
                senhaPerfil.isEnabled = true
                dddPerfil.isEnabled = true
                telefonePerfil.isEnabled = true

                // CPF continua bloqueado
                cpfPerfil.isEnabled = false

                editarPerfil.text = "Salvar"

            } else {

                db.updateUsuario(
                    cpfPerfil.text.toString(),
                    nomePerfil.text.toString(),
                    emailPerfil.text.toString(),
                    senhaPerfil.text.toString(),
                    dddPerfil.text.toString(),
                    telefonePerfil.text.toString()
                )

                nomePerfil.isEnabled = false
                emailPerfil.isEnabled = false
                senhaPerfil.isEnabled = false
                dddPerfil.isEnabled = false
                telefonePerfil.isEnabled = false
                cpfPerfil.isEnabled = false

                modoEdicao = false

                editarPerfil.text = "Editar"

                Toast.makeText(
                    this,
                    "Dados atualizados com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        excluirPerfil.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Excluir conta")
                .setMessage("Tem certeza que deseja excluir sua conta?")
                .setPositiveButton("Sim") { _, _ ->

                    val cpfUsuario = cpfPerfil.text.toString()

                    val linhasDeletadas =
                        db.deleteUsuario(cpfUsuario)

                    if (linhasDeletadas > 0) {

                        Toast.makeText(
                            this,
                            "Conta excluída com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(
                            this,
                            LoginActivity::class.java
                        )

                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK

                        startActivity(intent)

                        finish()

                    } else {

                        Toast.makeText(
                            this,
                            "Erro ao excluir conta.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
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