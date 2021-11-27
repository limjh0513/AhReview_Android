package com.hackathon.ahreview.data.service

import com.hackathon.ahreview.data.model.response.Store
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface StoreService {
    @GET("store")
    fun getStoreList(@Header("authorization") token: String): Single<Response<List<Store>>>
}