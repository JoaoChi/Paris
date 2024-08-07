package com.angellira.paris

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.angellira.paris.databinding.ActivityCadastroBinding
import com.angellira.paris.network.ParisApi
import com.angellira.paris.network.User
import com.angellira.paris.preferences.PreferencesManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private val parisApi = ParisApi.retrofitService
    lateinit var users: Map<String, User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding()
        validacaoCadastro()
    }

    private fun binding() {
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun validacaoCadastro() {
        binding.buttonCadastro.setOnClickListener {
            val name = binding.textNome.text.toString()
            val email = binding.textEmail.text.toString()
            val password = binding.textSenha.text.toString()
            val img = binding.botaoAddImagem.text.toString()
            val context = this

            if (email.contains('@')) {

                val user = User(name, email, password, img)
                lifecycleScope.launch {
                    val isValid = checkEmail(email)
                    if (isValid) {
                        cardJaExiste(email)
                    } else {
                        parisApi.saveUser(user)
                        val loginActivity = Intent(context, LoginActivity::class.java)
                        startActivity(loginActivity)
                    }
                }
            } else{
                Toast.makeText(this, "Por favor adicione um email que contenha @", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cardJaExiste(email: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.title))
            .setMessage(getString(R.string.supporting_text, suggestNewEmail(email)))

            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
            }
            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
            }
            .show()
    }

    private suspend fun checkEmail(email: String): Boolean {
        return if (email.isNotEmpty()) {
            users = parisApi.getUsers()
            users.values.any { it.email == email }
        } else {
            false
        }
    }

    fun suggestNewEmail(email: String): String {
        var suggestedEmail = email
        var counter = 1
        val existingEmails = users.values.map { it.email }

        while (existingEmails.contains(suggestedEmail)) {
            val emailParts = email.split("@")
            val localPart = emailParts[0]
            val domainPart = emailParts[1]

            suggestedEmail = "$localPart$counter@$domainPart"
            counter++
        }

        return suggestedEmail
    }
}

