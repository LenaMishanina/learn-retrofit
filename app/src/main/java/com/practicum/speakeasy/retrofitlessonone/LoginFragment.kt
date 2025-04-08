package com.practicum.speakeasy.retrofitlessonone

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.practicum.speakeasy.retrofitlessonone.databinding.FragmentLoginBinding
import com.practicum.speakeasy.retrofitlessonone.retrofit.MainApi
import com.practicum.speakeasy.retrofitlessonone.retrofit.auth.AuthRequest
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var mainApi: MainApi
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRetrofit()

        binding.apply {

            btnLogin.setOnClickListener {
                login(
                    AuthRequest(
                        edUsername.text.toString(),
                        edPassword.text.toString()
                    )
                )
            }

            btnNextFrag.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_postsFragment)
            }

        }
    }

    private fun initRetrofit() {
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
        mainApi = retrofit.create(MainApi::class.java)
    }

    private fun login(authRequest: AuthRequest) = with(binding) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = mainApi.auth(authRequest)
            requireActivity().runOnUiThread {
                if (response.isSuccessful) {
                    val user = response.body()
                    viewModel.token.value = user?.accessToken

                    tvName.text = user?.firstName
                    Picasso.get().load(user?.image).into(imageView)

                    btnNextFrag.visibility = View.VISIBLE
                    Log.d("MainActivityLog", "success")
                } else {
                    Toast.makeText(context, "User does not exist", Toast.LENGTH_SHORT).show()

                    tvName.text = ""
                    imageView.setImageResource(R.drawable.ic_launcher_foreground)

                    btnNextFrag.visibility = View.GONE
                    Log.d("MainActivityLog", "fail")
                }
            }
        }
    }

}