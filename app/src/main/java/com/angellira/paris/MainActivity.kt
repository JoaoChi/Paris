package com.angellira.paris

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.angellira.paris.databinding.ActivityMainBinding
import com.angellira.paris.model.MarsPhoto
import com.angellira.paris.network.MarsApi
import com.angellira.petvital1.recyclerview.adapter.FotosListAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private val marsApi = MarsApi.retrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        setSupportActionBar(findViewById(R.id.barra_tarefas))

        mandandoImagens()
        trocaFundo()
    }

    private fun trocaFundo() {
        binding.imageViewLogo.load("https://seeklogo.com/images/P/paris-2024-logo-EEA0228F1D-seeklogo.com.png")
    }

    private fun mandandoImagens() {
        lifecycleScope.launch {
            val listResult: List<MarsPhoto> = marsApi.getPhotos()
            Log.d("ListResult", "ListResult: ${listResult}")
            recyclerView = binding.textItensRecyclerview
            binding.textItensRecyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
            val adapter = FotosListAdapter(
                listResult,
                onItemClickListener = { source, id ->
                    val intent = Intent(this@MainActivity, ClickImagemMars::class.java)
                    intent.putExtra("photo_mars", source)
                    intent.putExtra("id_mars", id)
                    startActivity(intent)
                }
            )
            recyclerView.adapter = adapter
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

}