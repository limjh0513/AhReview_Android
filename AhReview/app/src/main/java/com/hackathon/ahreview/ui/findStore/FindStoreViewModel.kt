package com.hackathon.ahreview.ui.findStore

import androidx.lifecycle.MutableLiveData
import com.hackathon.ahreview.data.model.response.Store
import com.hackathon.ahreview.data.repository.StoreRepository
import com.hackathon.ahreview.ui.base.BaseViewModel
import com.hackathon.ahreview.utils.SingleLiveEvent
import io.reactivex.observers.DisposableSingleObserver

class FindStoreViewModel(private val storeRepository: StoreRepository) : BaseViewModel() {
    val backBtn = SingleLiveEvent<Any>()

    val getStoreListSuccess = MutableLiveData<List<Store>>()
    val getStoreListError = MutableLiveData<Throwable>()

    fun getStoreList(token: String) {
        addDisposable(storeRepository.getStoreList(token),
            object : DisposableSingleObserver<List<Store>>() {
                override fun onSuccess(t: List<Store>) {
                    getStoreListSuccess.value = t
                }

                override fun onError(e: Throwable) {
                    getStoreListError.value = e
                    e.printStackTrace()
                }

            })
    }

    fun onClickBackBtn() {
        backBtn.call()
    }
}