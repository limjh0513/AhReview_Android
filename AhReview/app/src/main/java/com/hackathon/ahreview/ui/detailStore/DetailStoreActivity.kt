package com.hackathon.ahreview.ui.detailStore

import android.content.Intent
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.hackathon.ahreview.R
import com.hackathon.ahreview.data.model.response.Store
import com.hackathon.ahreview.data.util.SharedPreferenceManager
import com.hackathon.ahreview.databinding.ActivityDetailStoreBinding
import com.hackathon.ahreview.ui.base.BaseActivity
import com.hackathon.ahreview.ui.writeReview.WriteReviewActivity
import kr.hs.dgsw.smartschool.morammoram.presentation.extension.shortToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailStoreActivity : BaseActivity<ActivityDetailStoreBinding, DetailStoreViewModel>() {

    // TODO: by viewModel 동작원리를 파악해라, 기존에는 ViewModelProvider를 사용했던 것을 기억하고
    override val viewModel: DetailStoreViewModel by viewModel()

    // FIXME: latenit은 최대한 사용하지 말고 Nullbale 하게 수정해라
    lateinit var store: Store

    // TODO: onResume 할 때마다 매번 getRevieList를 불러오는데, 성능면에서 좋을까
    // TODO: ReviewList가 수정되거나 업데이트 되면 매번 refresh 하지 말고 다른 방향을 생각해봐라
    // TODO: 리뷰가 수정되거나 업데이트 될 때 그때의 이벤트를 활용해서 기존의 List에 업데이트를 해줄 수도 있다, Coroutine, RxJava 비동기를 이럴 때 쓰는거다, 네트워크 통신 뿐만 아니라
    override fun onResume() {
        super.onResume()
        getReviewList(1)
    }

    override fun observerViewModel() {

        getStoreData()
        with(viewModel) {
            onClickWriteReview.observe(this@DetailStoreActivity, Observer {

                // FIXME: 각각의 extra로 넘기는 것보다 하나의 data class를 만들어서 넘기는 것이 좋다
                // FIXME: intent putextra 말고 다른 방법도 생각해라, SharedViewModel 이라던지 등등
                val intent = Intent(this@DetailStoreActivity, WriteReviewActivity::class.java)
                intent.putExtra("address", store.address)
                intent.putExtra("name", store.name)
                intent.putExtra("count", store.reviewAmount)
                intent.putExtra("score", store.starScore)
                intent.putExtra("address", store.address)
                intent.putExtra("url", store.url)

                startActivity(intent)
            })
            backBtn.observe(this@DetailStoreActivity, Observer {
                finish()
            })

            // FIXME: adapter를 매번 새롭게 만들어주고 있다, 성능적으로 좋지 못하다
            // FIXME: adpater를 공통으로 관리하고 한번 만들고 재사용할 수 있는 로직으로 해라
            // FIXME: notifyDataSetChanged는 엄청 좋지 못하다 성능적으로 모든 데이터를 바꾼다
            // FIXME: ListAdapter, DiffUtil 등 알아보고 공부해라
            getListSuccess.observe(this@DetailStoreActivity, Observer {
                val adapter = StoreReviewAdapter()
                adapter.context = this@DetailStoreActivity
                adapter.items = it
                adapter.store = store
                adapter.notifyDataSetChanged()
                mBinding.storeReviewRecycler.adapter = adapter
            })

            getListError.observe(this@DetailStoreActivity, Observer {
                shortToast("리스트를 받아오지 못했습니다.")
            })

            // FIXME: 색깔 바꾸거나 Visibility 처리 등 이런 로직을 DataBinding을 활용해서 BindingAdapter에서 처리하도록 해라
            // FIXME: 지금 작성하는 코드들이 최선이라고 생각하지만 레거시다, 레거시를 작성하고 있는거랑 똑같다
            // FIXME: getReviewList를 매번 또 호출하는데 스택, 힙 메모리 구조도 생각하면서 코드를 작성해라
            // FIXME: 성능적으로도 항상 생각하고, 현재는 좋지 못한 전체적인 로직이다 다시 데이터 흐름을 생각하고 수정해라
            recentBtn.observe(this@DetailStoreActivity, Observer {
                getReviewList(1)

                btnInit()
                mBinding.btnRecent.background = resources.getDrawable(R.drawable.activation_button_background)
                mBinding.btnRecent.setTextColor(resources.getColor(R.color.white))

            })

            positiveBtn.observe(this@DetailStoreActivity, Observer {
                getReviewList(2)
                btnInit()
                mBinding.btnPositive.background = resources.getDrawable(R.drawable.activation_button_background)
                mBinding.btnPositive.setTextColor(resources.getColor(R.color.white))
            })

            negativeBtn.observe(this@DetailStoreActivity, Observer {
                getReviewList(3)
                btnInit()
                mBinding.btnNegative.background = resources.getDrawable(R.drawable.activation_button_background)
                mBinding.btnNegative.setTextColor(resources.getColor(R.color.white))
            })
        }
    }

    // FIXME: bindingAdapter를 활용해서 처리할 수 있는 코드들이 엄청 많다
    // FIXME: bindingAdapter를 사용할려면 DataBinding에 대한 공부를 해야지만 할 수 있다

    fun btnInit(){
        mBinding.btnRecent.background = resources.getDrawable(R.drawable.disabled_button_background)
        mBinding.btnPositive.background = resources.getDrawable(R.drawable.disabled_button_background)
        mBinding.btnNegative.background = resources.getDrawable(R.drawable.disabled_button_background)

        mBinding.btnRecent.setTextColor(resources.getColor(R.color.black))
        mBinding.btnPositive.setTextColor(resources.getColor(R.color.black))
        mBinding.btnNegative.setTextColor(resources.getColor(R.color.black))

    }


    private fun getStoreData() {
        val name = intent.getStringExtra("storeName")
        val address = intent.getStringExtra("address")
        val ammount = intent.getIntExtra("ammount", 0)
        val score = intent.getIntExtra("score", 0)
        val url = intent.getStringExtra("url")

        mBinding.tvStoreDetailName.text = name
        mBinding.tvReviewNumber.text = "${ammount}개"
        mBinding.tvStarAverage.text = "${score}.0"
        mBinding.tvLocation1.text = address

        Glide.with(this)
            .load(url)
            .into(mBinding.storeImage)

        store = Store(address, name, ammount, score, url)
        getReviewList(1)
    }

    fun getReviewList(filter: Int){
        val token = SharedPreferenceManager.getToken(this)

        if(token != null){
            viewModel.getReviewList("Bearer $token", filter, store.address)
        } else {
            shortToast("토큰이 존재하지 않습니다.")
        }
    }
}