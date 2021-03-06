package com.my.apiproject.retrofit

import androidx.annotation.GuardedBy
import com.google.gson.GsonBuilder
import com.my.apiproject.support.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient private constructor() {

    private val baseUrl = Constants.baseUrl

    fun getApiService(): ApiInterface = apiInterface

    init {

        val client = OkHttpClient.Builder()
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS).addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .build()
                chain.proceed(newRequest)
            }.build()

        val gson = GsonBuilder().setLenient().create()

        val retrofit: Retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    companion object {

        private val lock = Any()

        @GuardedBy("lock")
        @Volatile
        private var instance: RetrofitClient? = null

        private lateinit var apiInterface: ApiInterface

        fun getInstance(): RetrofitClient = (instance ?: synchronized(lock) {
            instance = RetrofitClient()
            instance
        })!!

    }
}