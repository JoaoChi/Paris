package com.angellira.paris

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.angellira.paris.databinding.ActivityClickImagemMarsBinding


class ClickImagemMars : AppCompatActivity() {
    private lateinit var binding: ActivityClickImagemMarsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding()
        loadFotoID()
    }

    private fun binding() {
        binding = ActivityClickImagemMarsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun loadFotoID() {
        val pegandoId = intent.getStringExtra("id_mars")
        val pegandoPhoto = intent.getStringExtra("photo_mars")

        binding.imageOpen.load(pegandoPhoto)
        binding.textId.text = "id$pegandoId"
    }
}
