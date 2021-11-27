package com.hackathon.ahreview.data.model.response


data class SentimentResponse(
    val document: Document,
    val sentences: List<Sentence>
)