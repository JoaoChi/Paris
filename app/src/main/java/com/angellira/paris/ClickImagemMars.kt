package com.angellira.paris

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.angellira.paris.databinding.ActivityClickImagemMarsBinding

private lateinit var binding: ActivityClickImagemMarsBinding

class ClickImagemMars : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityClickImagemMarsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pegandoId = intent.getStringExtra("id_mars")
        val pegandoPhoto = intent.getStringExtra("photo_mars")

        binding.imageOpen.load(pegandoPhoto)
        binding.textId.text = "id$pegandoId"
    }
}
