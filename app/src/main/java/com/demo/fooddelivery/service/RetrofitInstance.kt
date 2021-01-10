package com.demo.fooddelivery.service

import android.util.Log.VERBOSE
import com.demo.fooddelivery.utils.Constants
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private const val BASE_URL = Constants.BASE_URL

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .client(makeOkHttpClient())


    private fun makeOkHttpClient(): OkHttpClient {
        val logInterceptor = LoggingInterceptor.Builder()
            .setLevel(Level.BASIC)
            .log(VERBOSE)
            .build()

        return OkHttpClient.Builder()
            .addNetworkInterceptor(logInterceptor)
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}
