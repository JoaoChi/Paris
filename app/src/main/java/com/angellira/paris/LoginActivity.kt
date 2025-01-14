package com.angellira.paris

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.angellira.paris.databinding.ActivityLoginBinding
import com.angellira.paris.network.ParisApi
import com.angellira.paris.network.ParisApiService
import com.angellira.paris.network.User
import com.angellira.paris.preferences.PreferencesManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferencesManager: PreferencesManager
    private val parisApi = ParisApi.retrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val SharedPref = getSharedPreferences(
            "USER_PREFERENCES", Context.MODE_PRIVATE
        )
        preferencesManager = PreferencesManager(this)
        setContentView(R.layout.activity_main)

            setupView()
            setupLoginButton(SharedPref)
            setupRegisterButton()

    }

    private fun setupLoginButton(SharedPref: SharedPreferences) {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val context = this

            lifecycleScope.launch {
                val isValid = checkCredentials(email, password, SharedPref)
                if (isValid) {
                    val startMain = Intent(context, MainActivity::class.java)
                    startActivity(startMain)
                    finish()
                    preferencesManager.isLogged = true
                } else {
                    preferencesManager.isLogged = false
                    Toast.makeText(context, "Email ou senha inválidos.", Toast.LENGTH_SHORT).show()
                }
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

        private suspend fun checkCredentials(email: String, password: String, SharedPref: SharedPreferences): Boolean {
            return if (email.isNotEmpty() && password.isNotEmpty()) {
                val users = parisApi.getUsers()
                preferencesManager.userId = users.entries.find { it.value.email == email }?.key
                SharedPref.edit().putString("Id", preferencesManager.userId).apply()
                users.values.any { it.email == email && it.password == password }
            } else {
                false
            }
        }
    }


