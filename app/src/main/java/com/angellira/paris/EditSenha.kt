package com.angellira.paris

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.angellira.paris.databinding.ActivityEditSenhaBinding
import com.angellira.paris.databinding.ActivityLoginBinding
import com.angellira.paris.databinding.ActivityProfileBinding
import com.angellira.paris.network.ParisApi
import com.angellira.paris.preferences.PreferencesManager
import kotlinx.coroutines.launch

class EditSenha : AppCompatActivity() {

    private lateinit var binding: ActivityEditSenhaBinding
    private lateinit var preferencesManager: PreferencesManager
    private val parisApi = ParisApi.retrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
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

    private suspend fun trocarSenha(password: String): Boolean {
        val senhanova = binding.textSenha1.text.toString()
        val senhanova2 = binding.textSenha2.text.toString()

        if(senhanova == senhanova2 && senhanova.isNotEmpty() && senhanova2.isNotEmpty())
        lifecycleScope.launch {
            try {
                val Id = preferencesManager.getUserID()
                parisApi.editUser(Id, user)


            } catch (e: Exception) {
            }
    }
}

