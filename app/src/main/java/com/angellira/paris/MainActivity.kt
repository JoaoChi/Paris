package com.angellira.paris

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.angellira.paris.model.MarsPhoto
import com.angellira.paris.databinding.ActivityMainBinding
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


        lifecycleScope.launch {
            val listResult: List<MarsPhoto> = marsApi.getPhotos()
            Log.d("ListResult", "ListResult: ${listResult}")
            recyclerView = binding.textItensRecyclerview
            binding.textItensRecyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
            val adapter = FotosListAdapter(listResult)
            recyclerView.adapter = adapter
        }





        binding.imageViewLogo.load("https://seeklogo.com/images/P/paris-2024-logo-EEA0228F1D-seeklogo.com.png")
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
}