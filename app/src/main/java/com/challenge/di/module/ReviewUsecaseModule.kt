package com.challenge.di.module

import com.challenge.data.repository.ReviewDataRepository
import com.challenge.di.scope.AppScope
import com.challenge.domain.interactor.GetReviewListUseCase
import dagger.Module
import dagger.Provides

@Module
open class ReviewUsecaseModule {
    @AppScope
    @Provides
    open fun provideGifListCase(repository: ReviewDataRepository) =
        GetReviewListUseCase(repository)
}
