package com.angellira.paris

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.angellira.paris.databinding.ActivityProfileBinding
import com.angellira.paris.network.ParisApi
import com.angellira.paris.network.User
import com.angellira.paris.preferences.PreferencesManager
import kotlinx.coroutines.launch
import okhttp3.Callback
import okhttp3.Response
import retrofit2.Call

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var preferencesManager: PreferencesManager
    private val parisApi = ParisApi.retrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        setSupportActionBar(findViewById(R.id.barra_tarefasProfile))
        preferencesManager = PreferencesManager(this)

        abriAtualizacaoSenha()
        abrirExcluirConta()
        abrirDeslogar()
    }

    private fun abrirDeslogar() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setMessage("Ao deslogar, você retornará para página de login, e sua conta será mantida.")
            .setTitle("Deslogar")
            .setPositiveButton("Deslogar") { dialog, wich ->
                Toast.makeText(this, "Deslogado", Toast.LENGTH_SHORT).show()
                preferencesManager.logout()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            .setNegativeButton("Cancelar") { dialog, wich ->
            }
        val dialog: AlertDialog = builder.create()
        binding.botaoDeslogar.setOnClickListener {
            dialog.show()
        }
    }

    private fun abrirExcluirConta() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setMessage("Certeza que deseja excluir sua conta? você não poderá recuperá-la depois!")
            .setTitle("Excluir Conta: ")
            .setPositiveButton("Sim") { dialog, wich ->
                startActivity(Intent(this, LoginActivity::class.java))
                deleteUser()
                Toast.makeText(this, "Sua conta foi deletada!", Toast.LENGTH_SHORT).show()
                preferencesManager.isLogged = false
                finish()
            }
            .setNegativeButton("Não") { dialog, wich ->
                Toast.makeText(this, "Sua conta não foi cancelada", Toast.LENGTH_SHORT).show()
            }
        val dialog: AlertDialog = builder.create()
        binding.botaoExcluirSenha.setOnClickListener {
            dialog.show()
        }
    }

    private fun abriAtualizacaoSenha() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setMessage("Certeza Que deseja alterar sua senha? Você será direcionado a página de alteração.")
            .setTitle("Atualizar Senha: ")
            .setPositiveButton("Sim") { dialog, wich ->
                startActivity(Intent(this, EditSenha::class.java))
            }
            .setNegativeButton("Cancelar") { dialog, wich ->
            }

        val dialog: AlertDialog = builder.create()
        binding.botaoAtualizaSenha.setOnClickListener {
            dialog.show()

        }
    }

    private fun setupView() {
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.itens, menu)
        return true
    }

    private fun deleteUser() {
        lifecycleScope.launch {
            val id = preferencesManager.userId
            parisApi.deleteUser(id.toString())
        }
    }
}
