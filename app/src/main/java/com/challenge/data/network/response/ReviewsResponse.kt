package com.challenge.data.network.response

data class ReviewsResponse(
    val reviews: List<ReviewRes>
)


data class ReviewRes(
    val id : String,
    val author :Author,
    val message : String,
    val rating : Int,
    val travelerType : String?
)


data class Author(
    val fullName: String,
    val country :String?
)


