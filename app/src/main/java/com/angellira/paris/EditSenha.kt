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
import retrofit2.Response

class EditSenha : AppCompatActivity() {

    private lateinit var binding: ActivityEditSenhaBinding
    private lateinit var preferencesManager: PreferencesManager
    val parisApi = ParisApi.retrofitService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        editSenha()

    }

    private fun editSenha() {
        binding.buttonConfirmar.setOnClickListener {
            lifecycleScope.launch {
                val meuID = preferencesManager.userId
                if (meuID.isNullOrEmpty()) {
                    Toast.makeText(this@EditSenha, "ID do usuário não encontrado", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                try {
                    val users = parisApi.getUser(meuID.toString())

                    val AtualPassword = users.password
                    val Nome = users.name
                    val email = users.email
                    val image = users.img

                    val novasenha = binding.textSenha1.text.toString()
                    val novasenha2 = binding.textSenha2.text.toString()

                    if (AtualPassword == novasenha) {
                        Toast.makeText(this@EditSenha, "Digite uma senha diferente da atual!", Toast.LENGTH_SHORT).show()
                    } else {
                        if (novasenha.isEmpty() || novasenha2.isEmpty()) {
                            Toast.makeText(this@EditSenha, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                        } else {
                            if (novasenha != novasenha2) {
                                Toast.makeText(this@EditSenha, "Os campos devem ser iguais", Toast.LENGTH_SHORT).show()
                            } else {
                                val editUser = User(users.name, users.email, novasenha, users.img)
                                val response = abriAtualizacaoSenha(editUser, meuID.toString())

                                if (response.isSuccessful) {
                                    Toast.makeText(this@EditSenha, "Senha atualizada com sucesso", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(this@EditSenha, "Erro ao atualizar a senha: ${response.message()}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@EditSenha, "Erro ao buscar usuário: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun abriAtualizacaoSenha(user: User, userId: String): Response<Unit> {
        return parisApi.editUser(userId, user)
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


