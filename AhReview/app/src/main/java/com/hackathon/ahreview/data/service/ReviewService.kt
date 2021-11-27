package com.hackathon.ahreview.data.service

import com.hackathon.ahreview.data.model.request.ReviewRequest
import com.hackathon.ahreview.data.model.response.StoreReview
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface ReviewService {
    @POST("review")
    fun postReview(
        @Header("authorization") token: String,
        @Body reviewRequest: ReviewRequest,
    ): Single<Response<Any>>

    @GET("review")
    fun getReview(
        @Header("authorization") token: String,
        @Query("filter") filter: Int,
        @Query("address") address: String,
    ): Single<Response<List<StoreReview>>>
}