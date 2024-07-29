package com.angellira.paris

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.angellira.paris.databinding.ActivityCadastroBinding
import com.angellira.paris.network.ParisApi
import com.angellira.paris.network.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
            val context = this

            val user = User(name, email, password, img)
            lifecycleScope.launch {
                val isValid = checkEmail(email)
                if (isValid){
                    MaterialAlertDialogBuilder(context)
                        .setTitle(resources.getString(R.string.title))
                        .setMessage(resources.getString(R.string.supporting_text))

                        .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                        }
                        .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                        }
                        .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                        }
                        .show()
                }
                else{
                    parisApi.saveUser(user)
                    val loginActivity = Intent(context, LoginActivity::class.java)
                    startActivity(loginActivity)
                }
            }
        }
    }
    private suspend fun checkEmail(email: String): Boolean {
        return if (email.isNotEmpty()) {
            val users = parisApi.getUsers()
            users.values.any { it.email == email }
        } else {
            false
        }
    }
    fun suggestNewEmail(existingEmails: List<String>, email: String): String {
        var suggestedEmail = email
        var counter = 1

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

