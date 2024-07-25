package com.angellira.paris

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.angellira.paris.databinding.ActivityClickImagemMarsBinding
import com.angellira.paris.databinding.ActivityLoginBinding
import com.angellira.paris.model.MarsPhoto


private lateinit var binding: ActivityClickImagemMarsBinding
private var preferences = MarsPhoto(img_src = "", id = "")

class ClickImagemMars : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityClickImagemMarsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageOpen.load(preferences.img_src)
        binding.textId.text = "Id da imagem que abriu:${preferences.id} "

        }
    }
