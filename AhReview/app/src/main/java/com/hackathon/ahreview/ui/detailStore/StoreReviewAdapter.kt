package com.hackathon.ahreview.ui.detailStore

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hackathon.ahreview.R
import com.hackathon.ahreview.data.model.response.Store
import com.hackathon.ahreview.data.model.response.StoreReview
import com.hackathon.ahreview.databinding.DetailReviewItemBinding
import com.hackathon.ahreview.utils.NaverAPITTS

class StoreReviewAdapter: RecyclerView.Adapter<StoreReviewAdapter.ViewHolder>() {

    lateinit var binding: DetailReviewItemBinding
    lateinit var items: List<StoreReview>
    lateinit var context: Context
    lateinit var store: Store

    // TODO: RecyclerView에 대해서 공부하고 구조에 대해 다시 공부해라, ListAdapter, PagingDataAdapter 등, Adapter를 매번 새롭게 만들어주는 건 좋지 않다
    // TODO: 공통된 Adapter는 하나로 묶어서 사용할 수 있도록, 생각하고 코딩해라, Adapter를 매번 파일로 안만들어줘도 되고 object : RecyclerView. Adapter 이렇게 inner로 처리해도 된다
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // TODO: 솔직히 말해서 parent, viewType 이거 모를거다, 왜 사용하는지 뭔지 공부해라
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.detail_review_item,
            parent,
            false)

        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], context)
    }

    override fun getItemCount(): Int {
        return items.size
    }



    // FIXME: DataBinding 공부가 필요하다 각각의 field에 다 set해주고 있는데 굳이 그럴 필요가 있을까, Item Xml에서 variable에 현재 아무것도 없자나
    // FIXME: 만약에 varaible에 vm처럼 data class 를 적어두면 그냥 binding.vm = VieModel 해주듯이 binding.item = Item 하면 데이터 바인딩이 된다, 공부해라
    inner class ViewHolder(binding: DetailReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(storeReview: StoreReview, context: Context) {
            Glide.with(context)
                .load(storeReview.urlList[0])
                .into(binding.userImage)
            if(storeReview.username == null){
                binding.reviewDName.text = "익명"
            } else {
                binding.reviewDName.text = storeReview.username
            }
            binding.reviewDContent.text = storeReview.review
            binding.imageBtnSpeaker2.setOnClickListener {
                NaverAPITTS().getTTS(storeReview.review)
            }

            binding.replyImage
            Glide.with(context)
                .load(store.url)
                .into(binding.replyImage)

            binding.replyName.text = store.name
            binding.replyContent.text = storeReview.answer
            binding.imageBtnSpeaker3.setOnClickListener {
                NaverAPITTS().getTTS(storeReview.answer)
            }
        }
    }

    fun starShow(cnt: Int){
        when(cnt){
            0->{
                binding.detailImageStar1.setImageResource(R.drawable.ic_off_star)
                binding.detailImageStar2.setImageResource(R.drawable.ic_off_star)
                binding.detailImageStar3.setImageResource(R.drawable.ic_off_star)
                binding.detailImageStar4.setImageResource(R.drawable.ic_off_star)
                binding.detailImageStar5.setImageResource(R.drawable.ic_off_star)
            }
            1->{
                binding.detailImageStar1.setImageResource(R.drawable.ic_on_star)
                binding.detailImageStar2.setImageResource(R.drawable.ic_off_star)
                binding.detailImageStar3.setImageResource(R.drawable.ic_off_star)
                binding.detailImageStar4.setImageResource(R.drawable.ic_off_star)
                binding.detailImageStar5.setImageResource(R.drawable.ic_off_star)
            }
            2->{
                binding.detailImageStar1.setImageResource(R.drawable.ic_on_star)
                binding.detailImageStar2.setImageResource(R.drawable.ic_on_star)
                binding.detailImageStar3.setImageResource(R.drawable.ic_off_star)
                binding.detailImageStar4.setImageResource(R.drawable.ic_off_star)
                binding.detailImageStar5.setImageResource(R.drawable.ic_off_star)
            }
            3->{
                binding.detailImageStar1.setImageResource(R.drawable.ic_on_star)
                binding.detailImageStar2.setImageResource(R.drawable.ic_on_star)
                binding.detailImageStar3.setImageResource(R.drawable.ic_on_star)
                binding.detailImageStar4.setImageResource(R.drawable.ic_off_star)
                binding.detailImageStar5.setImageResource(R.drawable.ic_off_star)
            }
            4->{
                binding.detailImageStar1.setImageResource(R.drawable.ic_on_star)
                binding.detailImageStar2.setImageResource(R.drawable.ic_on_star)
                binding.detailImageStar3.setImageResource(R.drawable.ic_on_star)
                binding.detailImageStar4.setImageResource(R.drawable.ic_on_star)
                binding.detailImageStar5.setImageResource(R.drawable.ic_off_star)
            }
            5->{
                binding.detailImageStar1.setImageResource(R.drawable.ic_on_star)
                binding.detailImageStar2.setImageResource(R.drawable.ic_on_star)
                binding.detailImageStar3.setImageResource(R.drawable.ic_on_star)
                binding.detailImageStar4.setImageResource(R.drawable.ic_on_star)
                binding.detailImageStar5.setImageResource(R.drawable.ic_on_star)
            }
        }
    }
}