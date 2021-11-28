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

    // TODO: Interceptor를 왜 사용하는지, Retrofit을 사용하는데 OkHTTP는 왜 사용하는지 알아야함
    // TODO: 원래 그냥 적어주던걸 왜 적어주는지 알고 사용해야 함

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