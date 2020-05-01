package com.challenge.ui.mapper

import com.challenge.domain.model.Review
import com.challenge.ui.model.ReviewModel

object ReviewModelMapper {
    private fun transform(review: Review): ReviewModel {
        return ReviewModel(
            review.id,
            review.authorName,
            review.authorCountry,
            review.message,
            review.rating,
            review.travelerType
        )
    }

    fun transform(reviewCollection: Collection<Review>): List<ReviewModel> {
        val reviewList = mutableListOf<ReviewModel>()
        for (review in reviewCollection) {
            val model = transform(review)
            if (model != null) {
                reviewList.add(model)
            }
        }

        return reviewList
    }
}
