package com.challenge.domain.repository

import com.challenge.domain.model.Review
import com.challenge.ui.dto.QueryDTO
import io.reactivex.Flowable

interface ReviewRepository {
    fun getReviewList(queryDTO: QueryDTO): Flowable<List<Review>>
}