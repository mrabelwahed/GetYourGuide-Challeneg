package com.challenge.di.module

import com.challenge.data.network.apis.ReviewApi
import com.challenge.data.repository.ReviewDataRepository
import com.challenge.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
open class RepositoryModule {
    @AppScope
    @Provides
    open fun provideReviewRepository(api: ReviewApi) =
        ReviewDataRepository(api)
}
