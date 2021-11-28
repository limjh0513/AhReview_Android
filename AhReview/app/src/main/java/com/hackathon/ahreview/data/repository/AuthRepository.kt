package com.hackathon.ahreview.data.repository

import com.hackathon.ahreview.data.model.request.TokenLoginRequest
import com.hackathon.ahreview.data.model.response.Login
import com.hackathon.ahreview.data.service.AuthService
import io.reactivex.Single
import org.json.JSONObject

// TODO: 로그를 달자 로그를, Logcat에 아무것도 안찍힌다, 프로젝트가 커지면 어디서 에러가 발생했는지 확인하기 힘들다
// TODO: 항상 현재의 프로젝트 상황말고 앞으로 더 커지면 어떻게 될지 생각하면서 코딩해라
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