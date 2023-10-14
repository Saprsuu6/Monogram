package com.example.monogram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.monogram.databinding.ActivityAuthendifocatoionBinding

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthendifocatoionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthendifocatoionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}