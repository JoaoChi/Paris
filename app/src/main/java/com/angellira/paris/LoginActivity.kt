package com.angellira.paris

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.angellira.paris.databinding.ActivityLoginBinding
import com.angellira.paris.network.ParisApi
import com.angellira.paris.network.User
import com.angellira.paris.preferences.PreferencesManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferencesManager: PreferencesManager
    private val parisApi = ParisApi.retrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferencesManager = PreferencesManager(this)

        setupView()
        setupLoginButton()
        setupRegisterButton()
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

//            val users = ParisApi.retrofitService.getUsers()
//            val usersExist = users.values.any { it.email == email && it.password == password }

            if (checkCredentials(email, password)) {
                preferencesManager.isLogged = true
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Email ou senha inválidos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRegisterButton() {
        binding.registerTextView.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
    }

    private fun setupView() {
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(64, systemBars.top, 64, systemBars.bottom)
            insets
        }
    }

    private fun checkCredentials(email: String, password: String): Boolean {
        val isValid = email.isNotEmpty() && password.isNotEmpty()


        // buscar no webapi se o email e senha são válidos e retornar true ou false

        return isValid
    }
}
