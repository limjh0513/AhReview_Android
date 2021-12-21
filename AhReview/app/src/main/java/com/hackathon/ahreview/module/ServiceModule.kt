package com.hackathon.ahreview.module

import com.hackathon.ahreview.data.service.AuthService
import com.hackathon.ahreview.data.service.ReviewService
import com.hackathon.ahreview.data.service.StoreService
import com.hackathon.ahreview.data.service.UserService
import org.koin.dsl.module
import retrofit2.Retrofit

val ServiceModule = module {
    single { (get() as Retrofit).create(AuthService::class.java) }
    single { (get() as Retrofit).create(ReviewService::class.java) }
    single { (get() as Retrofit).create(StoreService::class.java) }
    single { (get() as Retrofit).create(UserService::class.java) }
}