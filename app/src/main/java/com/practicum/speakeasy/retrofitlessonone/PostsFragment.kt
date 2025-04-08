package com.practicum.speakeasy.retrofitlessonone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.speakeasy.retrofitlessonone.databinding.FragmentPostsBinding
import com.practicum.speakeasy.retrofitlessonone.retrofit.MainApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostsFragment : Fragment() {

    private lateinit var binding: FragmentPostsBinding
    private lateinit var mainApi: MainApi
    private val adapter = PostAdapter()
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRetrofit()

        viewModel.token.observe(viewLifecycleOwner) { accessToken ->
            initRcView(accessToken)
            searchPosts(accessToken)
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

    private fun initRcView(token: String) = with(binding) {
        rcPosts.layoutManager = LinearLayoutManager(context)
        rcPosts.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            val response = token.let { mainApi.getPostsAuth(it) }
            requireActivity().runOnUiThread {
                if (response.isSuccessful)
                    adapter.submitList(response.body()?.posts)
                else
                    Toast.makeText(context, "Posts couldn't be uploaded", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchPosts(token: String) = with(binding) {
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                    val response = text?.let { mainApi.getSearchPostsAuth(token, it) }
                    requireActivity().runOnUiThread {
                        if (response != null) {
                            if (response.isSuccessful)
                                adapter.submitList(response.body()?.posts)
                            else
                                Toast.makeText(context, "Posts couldn't be uploaded", Toast.LENGTH_SHORT).show()
                        }
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