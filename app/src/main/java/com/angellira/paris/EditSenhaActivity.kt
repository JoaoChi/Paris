package com.angellira.paris

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.angellira.paris.databinding.ActivityEditSenhaBinding
import com.angellira.paris.network.ParisApi
import com.angellira.paris.network.User
import com.angellira.paris.preferences.PreferencesManager
import kotlinx.coroutines.launch

class EditSenhaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditSenhaBinding
    private lateinit var preferencesManager: PreferencesManager
    val parisApi = ParisApi.retrofitService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesManager = PreferencesManager(this)

        printarDados()
        setupView()
        editSenha()
        botaoVoltar()
    }

    private fun printarDados() {
        lifecycleScope.launch {
            val meuID = preferencesManager.userId
            val users = parisApi.getUser(meuID.toString())

            val AtualPassword = users.password
            val Nome = users.name
            val email = users.email

            binding.textemail.text = "Email: ${email}"
            binding.textusername.text = "User: ${Nome}"
            binding.textsenhaAtual.text = "Senha atual: ${AtualPassword}"
        }
    }

    private fun editSenha() {
        binding.buttonConfirmar.setOnClickListener {
            lifecycleScope.launch {
                val meuID = preferencesManager.userId
                if (meuID.isNullOrEmpty()) {
                    Toast.makeText(this@EditSenhaActivity, "ID do usuário não encontrado", Toast.LENGTH_SHORT).show()
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

                    if (AtualPassword != novasenha
                        && novasenha2.isNotEmpty()
                        && novasenha.isNotEmpty()
                        && novasenha == novasenha2) {
                        val editarUsuario = User(Nome, email, novasenha, image)
                        val response = parisApi.editUser(meuID, editarUsuario)

                        if (response.isSuccessful) {
                            Toast.makeText(this@EditSenhaActivity, "Senha atualizada com sucesso", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@EditSenhaActivity, MainActivity::class.java))
                        } else {
                            Toast.makeText(this@EditSenhaActivity, "Erro ao atualizar a senha.", Toast.LENGTH_SHORT).show()
                        }
                    }

                } catch (e: Exception) {
                    Toast.makeText(this@EditSenhaActivity, "Erro ao buscar usuário.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


private fun botaoVoltar(){
    binding.buttonVolta.setOnClickListener(){
        startActivity(Intent(this, ProfileActivity::class.java))
    }
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


