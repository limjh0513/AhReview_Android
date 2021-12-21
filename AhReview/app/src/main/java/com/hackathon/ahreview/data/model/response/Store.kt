package com.hackathon.ahreview.data.model.response


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Store(
    val address: String,
    val name: String,
    @SerializedName("review_amount")
    val reviewAmount: Int,
    @SerializedName("star_score")
    var starScore: Int = 0,
    val url: String,
) : Serializable
// serializable을 implemetion해서 intent를 사용할 때 serializable,
// 즉 Store 객체를 activity 간에 넘길 수 있도록 했음