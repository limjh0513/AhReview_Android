package com.hackathon.ahreview.module

import com.hackathon.ahreview.data.service.*
import org.koin.dsl.module
import retrofit2.Retrofit

val ServiceModule = module {
    single { (get() as Retrofit).create(AuthService::class.java) }
    single { (get() as Retrofit).create(ReviewService::class.java) }
    single { (get() as Retrofit).create(StoreService::class.java) }
    single { (get() as Retrofit).create(UserService::class.java) }
}