package com.hackathon.ahreview.data.repository

import com.hackathon.ahreview.data.model.response.Store
import com.hackathon.ahreview.data.service.StoreService
import io.reactivex.Single
import org.json.JSONObject

class StoreRepository(private val service: StoreService) {
    fun getStoreList(token: String): Single<List<Store>> {
        return service.getStoreList(token).map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.body().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.body()
        }
    }
}