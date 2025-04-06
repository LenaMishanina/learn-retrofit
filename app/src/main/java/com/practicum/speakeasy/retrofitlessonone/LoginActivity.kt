package com.practicum.speakeasy.retrofitlessonone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practicum.speakeasy.retrofitlessonone.databinding.ActivityLoginBinding
import com.practicum.speakeasy.retrofitlessonone.retrofit.MainApi
import com.practicum.speakeasy.retrofitlessonone.retrofit.auth.AuthRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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
        val mainApi = retrofit.create(MainApi::class.java)

        binding.apply {
            btnLogin.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val user = mainApi.auth(
                        AuthRequest(
                            edUsername.text.toString(),
                            edPassword.text.toString()
                        )
                    )
                    runOnUiThread {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("token", user.accessToken)
                        startActivity(intent)
                    }
                }
            }
        }

    }
}