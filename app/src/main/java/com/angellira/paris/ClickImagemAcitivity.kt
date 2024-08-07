package com.angellira.paris

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.angellira.paris.databinding.ActivityClickImagemMarsBinding


class ClickImagemAcitivity : AppCompatActivity() {
    private lateinit var binding: ActivityClickImagemMarsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding()
        loadFotoID()
    }

    private fun abrirCalendario(context: Context, pegandoNome: String) {
            binding.botaoCalendario.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val pegandoNome = pegandoNome?.toLowerCase()
            val url = ("https://olympics.com/pt/paris-2024/calendario/$pegandoNome")
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }
    }

    private fun abrirMedalhas(context: Context, pegandoNome: String) {
            binding.botaoMedalhas.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val pegandoNome = pegandoNome?.toLowerCase()
            val url = ("https://olympics.com/pt/paris-2024/esportes/$pegandoNome")
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }
    }

    private fun binding() {
        binding = ActivityClickImagemMarsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun loadFotoID() {
        val pegandoNome = intent.getStringExtra("nome_esporte")
        val pegandoPhoto = intent.getStringExtra("photo_mars")
        val pegandoDescricao = intent.getStringExtra("descricao")

        binding.textDescription.text = "Descrição: $pegandoDescricao"
        binding.imageOpen.load(pegandoPhoto)
        binding.textId.text = "Nome: $pegandoNome"

        abrirCalendario(this, pegandoNome.toString())
        abrirMedalhas(this, pegandoNome.toString())
    }
}
