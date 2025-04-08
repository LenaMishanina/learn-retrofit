package com.practicum.speakeasy.retrofitlessonone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practicum.speakeasy.retrofitlessonone.databinding.ContentMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ContentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ContentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}
