package com.hackathon.ahreview.data.model.response


data class Sentence(
    val confidence: ConfidenceX,
    val content: String,
    val highlights: List<Highlight>,
    val length: Int,
    val offset: Int,
    val sentiment: String
)