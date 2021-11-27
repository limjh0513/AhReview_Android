package com.hackathon.ahreview.data.repository

import com.hackathon.ahreview.data.model.request.ReviewRequest
import com.hackathon.ahreview.data.model.response.StoreReview
import com.hackathon.ahreview.data.service.ReviewService
import io.reactivex.Single
import org.json.JSONObject

class ReviewRepository(private val service: ReviewService) {
    fun postReview(token: String, reviewRequest: ReviewRequest): Single<Any> {
        return service.postReview(token, reviewRequest).map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.body().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.body()
        }
    }

    fun getReview(token: String, filter: Int, address: String): Single<List<StoreReview>> {
        return service.getReview(token, filter, address).map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.body().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.body()
        }
    }
}