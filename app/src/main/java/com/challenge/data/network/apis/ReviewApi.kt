package com.challenge.data.network.apis

import com.challenge.data.network.response.ReviewsResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ReviewApi {

    @Headers("User-Agent: GetYourGuide")
    @GET("activities/23776/reviews")
    fun getReviews(
        @Query("limit") limit: Int,
        @Query("offset") offset: Long
    ): Flowable<ReviewsResponse>
}
