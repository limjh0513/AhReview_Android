package com.hackathon.ahreview.ui.login

import androidx.lifecycle.MutableLiveData
import com.hackathon.ahreview.data.model.response.Login
import com.hackathon.ahreview.data.repository.AuthRepository
import com.hackathon.ahreview.ui.base.BaseViewModel
import io.reactivex.observers.DisposableSingleObserver

class LoginViewModel(private val authRepository: AuthRepository) : BaseViewModel() {

    val loginSuccess = MutableLiveData<Login>()
    val loginError = MutableLiveData<Throwable>()


    fun login(token: String) {
        addDisposable(authRepository.tokenLogin(token), object : DisposableSingleObserver<Login>() {
            override fun onSuccess(t: Login) {
                loginSuccess.value = t
            }

            override fun onError(e: Throwable) {
                loginError.value = e
            }

        })
    }
}