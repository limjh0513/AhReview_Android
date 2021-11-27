package com.hackathon.ahreview.data.repository

import com.hackathon.ahreview.data.model.response.UserInfo
import com.hackathon.ahreview.data.service.UserService
import io.reactivex.Single
import org.json.JSONObject

class UserRepository(private val service: UserService) {
    fun getUserInfo(token: String): Single<UserInfo> {
        return service.getUserInfo(token).map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.body().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.body()
        }
    }
}