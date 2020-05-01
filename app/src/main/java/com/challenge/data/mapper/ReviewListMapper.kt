package com.challenge.data.mapper

import com.challenge.data.network.response.ReviewRes
import com.challenge.domain.model.Review

object ReviewListMapper {
    private fun transform(res: ReviewRes): Review {
        if (res.message.isEmpty()) {
            val consMessage = "No Message Yet"
            return Review(
                res.id,
                res.author.fullName,
                res.author.country,
                consMessage,
                res.rating,
                res.travelerType
            )
        }

        return Review(
            res.id,
            res.author.fullName,
            res.author.country,
            res.message,
            res.rating,
            res.travelerType
        )
    }

    fun transform(responseCollection: Collection<ReviewRes>): List<Review> {
        val reviewList = mutableListOf<Review>()
        for (reviewRes in responseCollection) {
            val feed = transform(reviewRes)
            if (feed != null) {
                reviewList.add(feed)
            }
        }

        return reviewList
    }
}
