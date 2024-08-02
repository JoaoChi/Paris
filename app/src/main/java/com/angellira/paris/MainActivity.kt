package com.angellira.paris

import android.content.Intent
import android.os.Bundle
import android.service.voice.VisibleActivityInfo
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.angellira.paris.databinding.ActivityMainBinding
import com.angellira.paris.model.SportPhoto
import com.angellira.paris.network.MarsApi
import com.angellira.petvital1.recyclerview.adapter.FotosListAdapter
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private val olimpiadas = MarsApi.retrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        setSupportActionBar(findViewById(R.id.barra_tarefas))

        try {
            fetchDataFromServer()
        }catch(e: NoInternetConnectionException) {
                println("Erro: ${e.message}")
            }
        
        mandandoImagens()
        trocaFundo()
    }

    private fun trocaFundo() {
        binding.imageViewLogo.load("https://seeklogo.com/images/P/paris-2024-logo-EEA0228F1D-seeklogo.com.png")
    }

    private fun mandandoImagens() {
            lifecycleScope.launch {
                delay(1.seconds)
                val listSports = olimpiadas.getPhotos().values.toList()
                Log.d("ListResult", "ListResult: ${listSports}")
                recyclerView = binding.textItensRecyclerview
                binding.textItensRecyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
                val adapter = FotosListAdapter(
                    listSports,
                    onItemClickListener = { source, nome, descricao ->
                        val intent = Intent(this@MainActivity, ClickImagemMars::class.java)
                        intent.putExtra("descricao", descricao)
                        intent.putExtra("photo_mars", source)
                        intent.putExtra("nome_esporte", nome)
                        startActivity(intent)
                    }
                )
                recyclerView.adapter = adapter
                binding.mainLayout.visibility = VISIBLE
                binding.loadingLayout.visibility = GONE
            }
        }


    private fun setupView() {
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile_action -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    class NoInternetConnectionException(message: String) : Exception(message)


    private fun fetchDataFromServer() {
        val hasInternetConnection = false

        if (!hasInternetConnection) {
            throw NoInternetConnectionException("Não há conexão à internet.")
        }
    }

}