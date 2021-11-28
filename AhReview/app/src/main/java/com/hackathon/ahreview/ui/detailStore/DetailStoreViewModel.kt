package com.hackathon.ahreview.ui.detailStore

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.hackathon.ahreview.data.model.response.StoreReview
import com.hackathon.ahreview.data.repository.ReviewRepository
import com.hackathon.ahreview.ui.base.BaseViewModel
import com.hackathon.ahreview.utils.SingleLiveEvent
import io.reactivex.observers.DisposableSingleObserver


// FIXME: Mutable은 변경가능한 LiveData이다, ViewModel에서는 Mutable을 사용하고 외부에서는 LiveData로 접근할 수 있도록 해야 한다
// FIXME: Mutable 변수를 private으로 LiveData를 public으로 해서 사용해야 한다, LiveData를 왜 사용하는지 AAC 등에 대해서 자세히 공부해라
// FIXME: SingleLiveEvent는 왜 사용하는지 등, ViewModel의 역할이 뭔지 생각해라, XML로 부터 지금 이벤트 받고 다시 View로 넘겨주는거 밖에 없는데 이게 과연 제대로된 걸까

class DetailStoreViewModel(private val reviewRepository: ReviewRepository) : BaseViewModel() {

    val onClickWriteReview = SingleLiveEvent<Unit>()

    val backBtn = SingleLiveEvent<Any>()

    val getListSuccess = MutableLiveData<List<StoreReview>>()
    val getListError = MutableLiveData<Throwable>()

    val recentBtn = SingleLiveEvent<Any>()
    val positiveBtn = SingleLiveEvent<Any>()
    val negativeBtn = SingleLiveEvent<Any>()

    fun onClickWriteReview(view: View) {
        onClickWriteReview.call()
    }

    fun onClickBackBtn() {
        backBtn.call()
    }

    fun onClickRecentBtn() {
        recentBtn.call()
    }

    fun onClickPositiveBtn() {
        positiveBtn.call()
    }

    fun onClickNegativeBtn() {
        negativeBtn.call()
    }

    fun getReviewList(token: String, filter: Int, address: String) {
        addDisposable(reviewRepository.getReview(token, filter, address),
            object : DisposableSingleObserver<List<StoreReview>>() {

                override fun onSuccess(t: List<StoreReview>) {
                    getListSuccess.value = t

                }

                override fun onError(e: Throwable) {
                    getListError.value = e
                    e.printStackTrace()
                }

            })
    }
}