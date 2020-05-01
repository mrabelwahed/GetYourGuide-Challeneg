package com.challenge.domain.interactor

import com.challenge.domain.model.Review
import com.challenge.domain.repository.ReviewRepository
import com.challenge.ui.dto.QueryDTO
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetReviewListUseCase(private val reviewRespository: ReviewRepository) :
    UseCase<QueryDTO, List<Review>> {
    override fun execute(param: QueryDTO): Flowable<List<Review>> {
        return reviewRespository.getReviewList(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
