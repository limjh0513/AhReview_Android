package com.hackathon.ahreview.data.service

import com.hackathon.ahreview.data.model.request.TokenLoginRequest
import com.hackathon.ahreview.data.model.response.Login
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login/token")
    fun tokenLogin(@Body request: TokenLoginRequest): Single<Response<Login>>
}