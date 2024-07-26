package com.angellira.paris

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.angellira.paris.databinding.ActivityCadastroBinding
import com.angellira.paris.network.ParisApi
import com.angellira.paris.network.User
import kotlinx.coroutines.launch

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private val parisApi = ParisApi.retrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCadastro.setOnClickListener {
            val name = binding.textNome.text.toString()
            val email = binding.textEmail.text.toString()
            val password = binding.textSenha.text.toString()
            val img = binding.botaoAddImagem.text.toString()

            val user = User(name, email, password, img)
            lifecycleScope.launch {
                parisApi.saveUser(user)
            }

            val loginActivity = Intent(this, LoginActivity::class.java)
            startActivity(loginActivity)


        }
    }
}

