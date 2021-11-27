package com.hackathon.ahreview.data.repository

import com.hackathon.ahreview.data.model.request.TokenLoginRequest
import com.hackathon.ahreview.data.model.response.Login
import com.hackathon.ahreview.data.service.AuthService
import io.reactivex.Single
import org.json.JSONObject

class AuthRepository(private val service: AuthService) {
    fun tokenLogin(token: String): Single<Login> {
        val request = TokenLoginRequest(token)
        return service.tokenLogin(request).map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.body().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.body()
        }
    }
}