package com.hackathon.ahreview.ui.writeReview

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.hackathon.ahreview.data.model.request.ReviewRequest
import com.hackathon.ahreview.data.model.response.SentimentResponse
import com.hackathon.ahreview.data.repository.ClovaRepository
import com.hackathon.ahreview.data.repository.ReviewRepository
import com.hackathon.ahreview.ui.base.BaseViewModel
import com.hackathon.ahreview.utils.SingleLiveEvent
import io.reactivex.observers.DisposableSingleObserver

class WriteReviewViewModel(
    val reviewRepository: ReviewRepository,
    private val clovaRepository: ClovaRepository,
) : BaseViewModel() {

    val onClickedAnonymous = SingleLiveEvent<Unit>()
    val onMicClicked = SingleLiveEvent<Unit>()
    val onReviewed = SingleLiveEvent<Unit>()

    val anonymous = MutableLiveData(false)
    val review = MutableLiveData("")
    val ratingStar = MutableLiveData(0.0F)
    val imageUrl = MutableLiveData("")

    val sentimentSuccess = MutableLiveData<SentimentResponse>()
    val sentimentError = MutableLiveData<Throwable>()

    val writeReviewSuccess = MutableLiveData<Any>()
    val writeReviewError = MutableLiveData<Throwable>()

    fun getSentiment(id: String, key: String, type: String, review: String) {
        addDisposable(clovaRepository.checkSentiment(id, key, type, review), object: DisposableSingleObserver<SentimentResponse>(){
            override fun onSuccess(t: SentimentResponse) {
                sentimentSuccess.value = t
            }

            override fun onError(e: Throwable) {
                sentimentError.value = e
                e.printStackTrace()
            }
        })
    }

    fun writeReview(token: String, request: ReviewRequest){
        addDisposable(reviewRepository.postReview(token, request), object : DisposableSingleObserver<Any>(){
            override fun onSuccess(t: Any) {
                writeReviewSuccess.value = t
            }

            override fun onError(e: Throwable) {
                writeReviewError.value = e
            }

        })
    }

    fun onCheckedAnonymous(view: View) {
        onClickedAnonymous.call()
    }

    fun onMicClicked(view: View) {
        onMicClicked.call()
    }

    fun onReviewChecked(view: View) {
        onReviewed.call()
    }
}