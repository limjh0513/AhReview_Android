package com.hackathon.ahreview.ui.main

import androidx.lifecycle.MutableLiveData
import com.hackathon.ahreview.data.model.response.UserInfo
import com.hackathon.ahreview.data.repository.UserRepository
import com.hackathon.ahreview.ui.base.BaseViewModel
import com.hackathon.ahreview.utils.SingleLiveEvent
import io.reactivex.observers.DisposableSingleObserver

class MainViewModel(private val userRepository: UserRepository) : BaseViewModel() {
    val userName = MutableLiveData<String>()
    val findStoreBtn = SingleLiveEvent<Any>()

    val getUserInfoSuccess = MutableLiveData<UserInfo>()
    val getUserInfoError = MutableLiveData<Throwable>()

    fun getUserInfo(token: String) {
        addDisposable(userRepository.getUserInfo(token),
            object : DisposableSingleObserver<UserInfo>() {
                override fun onSuccess(t: UserInfo) {
                    getUserInfoSuccess.value = t
                }

                override fun onError(e: Throwable) {
                    getUserInfoError.value = e
                }
            })
    }

    fun onClickFindStoreBtn() {
        findStoreBtn.call()
    }

}