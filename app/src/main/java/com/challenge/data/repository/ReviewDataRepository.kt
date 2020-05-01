package com.challenge.data.repository

import com.challenge.AppConst.LIMIT
import com.challenge.data.mapper.ReviewListMapper
import com.challenge.data.network.apis.ReviewApi
import com.challenge.domain.model.Review
import com.challenge.domain.repository.ReviewRepository
import com.challenge.ui.dto.QueryDTO
import io.reactivex.Flowable

class ReviewDataRepository(private val reviewAPI: ReviewApi) : ReviewRepository {

    override fun getReviewList(queryDTO: QueryDTO): Flowable<List<Review>> {
        return reviewAPI.getReviews(LIMIT, queryDTO.offset)
            .map { res -> ReviewListMapper.transform(res.reviews) }
    }
}
