package com.practicum.speakeasy.retrofitlessonone

import android.os.Bundle
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.speakeasy.retrofitlessonone.databinding.ActivityMainBinding
import com.practicum.speakeasy.retrofitlessonone.retrofit.MainApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = PostAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val accessToken = intent.getStringExtra("token")

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
        val mainApi = retrofit.create(MainApi::class.java)

        binding.apply {
            rcPosts.layoutManager = LinearLayoutManager(this@MainActivity)
            rcPosts.adapter = adapter

            CoroutineScope(Dispatchers.IO).launch {
                val posts = accessToken?.let { mainApi.getPostsAuth(it) }
                runOnUiThread {
                    adapter.submitList(posts?.posts)
                }
            }

            searchView.setOnQueryTextListener(object: OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean {
                    CoroutineScope(Dispatchers.IO).launch {
                        val searchPosts = text?.let { mainApi.getSearchPostsAuth(accessToken ?: "", it) }
                        runOnUiThread {
                            adapter.submitList(searchPosts?.posts)
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return true
                }

            })

        }
    }
}