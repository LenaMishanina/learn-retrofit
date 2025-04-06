package com.practicum.speakeasy.retrofitlessonone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practicum.speakeasy.retrofitlessonone.databinding.ActivityMainBinding
import com.practicum.speakeasy.retrofitlessonone.retrofit.PostApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
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