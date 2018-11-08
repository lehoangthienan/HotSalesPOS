package com.uit.daniel.hotsalesmanager.service

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface ApiService {

    object Factory {
        private const val DEFAULT_TIMEOUT_SECONDS = 60L


        fun getRetrofitBuilder(context: Context): Retrofit.Builder {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
                        .header("Authorization", "" + null)
                        .method(original.method(), original.body())
                        .build()
                    chain.proceed(request)
                }
                .addInterceptor(loggingInterceptor)
                .readTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build()
            val gson = GsonBuilder().create()

            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)

        }
    }


}