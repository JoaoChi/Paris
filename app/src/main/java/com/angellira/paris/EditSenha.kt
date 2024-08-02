package com.angellira.paris

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.angellira.paris.databinding.ActivityEditSenhaBinding
import com.angellira.paris.databinding.ActivityLoginBinding
import com.angellira.paris.databinding.ActivityProfileBinding
import com.angellira.paris.network.ParisApi
import com.angellira.paris.network.ParisApiService
import com.angellira.paris.network.User
import com.angellira.paris.preferences.PreferencesManager
import kotlinx.coroutines.launch

class EditSenha : AppCompatActivity() {

    private lateinit var binding: ActivityEditSenhaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ParisApi = ParisApi.retrofitService
        val SharedPref = getSharedPreferences(
            "Preferencias", Context.MODE_PRIVATE
        )

        setupView()

        editSenha(SharedPref, ParisApi)
    }

    private fun editSenha(SharedPref: SharedPreferences, ParisApi: ParisApiService) {
        binding.buttonConfirmar.setOnClickListener {
            lifecycleScope.launch {
                val meuID = SharedPref.getString("Id", "")
                val users = ParisApi.getUser(meuID.toString())

                val AtualPassword = users.password
                val Nome = users.name
                val email = users.email
                val image = users.img

                val novasenha = binding.textSenha1.text.toString()
                val novasenha2 = binding.textSenha2.text.toString()

                if (AtualPassword == novasenha) {
                    Toast.makeText(
                        this@EditSenha,
                        "Digite uma senha diferente da atual!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (novasenha.isEmpty() || novasenha2.isEmpty()) {
                        Toast.makeText(
                            this@EditSenha,
                            "Preencha todos os campos!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (novasenha != novasenha2) {
                            Toast.makeText(
                                this@EditSenha,
                                "Os campos devem ser iguais",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val PutUser = User(users.name, users.email, novasenha, users.img)
                            abriAtualizacaoSenha(SharedPref, ParisApi, PutUser, meuID.toString())
                        }
                    }
                }
            }
        }
    }

    private fun abriAtualizacaoSenha(
        SharedPref: SharedPreferences,
        ParisApi: ParisApiService,
        PutUser : User,
        meuId : String
    ){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setTitle("Alteração de senha!")
            .setMessage("Tem certeza que deseja Altera a Senha da sua conta?")
            .setPositiveButton("Sim") { dialog, which ->
                lifecycleScope.launch {
                    ParisApi.editUser(meuId, PutUser)
                    startActivity(Intent(this@EditSenha, ProfileActivity::class.java))
                    finish()
                }
            }
            .setNegativeButton("Não") {
                dialog, wich ->
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }



    private fun setupView() {
        enableEdgeToEdge()
        binding = ActivityEditSenhaBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}


