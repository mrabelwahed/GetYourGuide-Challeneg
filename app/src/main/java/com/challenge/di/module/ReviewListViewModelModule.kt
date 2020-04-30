package com.challenge.di.module

import androidx.lifecycle.ViewModel
import com.challenge.ui.viewmodel.ReviewListViewModel
import com.challenge.ui.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ReviewListViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ReviewListViewModel::class)
    internal abstract fun bindGifListViewModel(viewModel: ReviewListViewModel): ViewModel
}