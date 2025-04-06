package com.practicum.speakeasy.retrofitlessonone

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.practicum.speakeasy.retrofitlessonone.databinding.ActivityMainBinding
import com.practicum.speakeasy.retrofitlessonone.retrofit.PostApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val postApi = retrofit.create(PostApi::class.java)

        binding.apply {
            btnGetTitlePost.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val post4 = postApi.getPostById(4)
                    runOnUiThread {
                        tvTitlePost.text = post4.title
                    }
                }
            }
        }
    }
}