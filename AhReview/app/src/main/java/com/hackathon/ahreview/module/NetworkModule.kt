package com.hackathon.ahreview.module

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val NetworkServiceModule = module {

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder().addNetworkInterceptor(interceptor)
            .build()
    }

    single {
        RxJava2CallAdapterFactory.create() as CallAdapter.Factory
    }

    single {
        GsonConverterFactory.create() as Converter.Factory
    }

    single {
        Retrofit.Builder()
            .baseUrl("http://27.96.134.100:8080/")
            .client(get())
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .build()
    }
}