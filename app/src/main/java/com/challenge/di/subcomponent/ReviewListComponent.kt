package com.challenge.di.subcomponent

import com.challenge.di.module.ReviewListViewModelModule
import com.challenge.di.module.ViewModelFactoryModule
import com.challenge.di.scope.FragmentScope
import com.challenge.ui.ReviewListFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(
    modules = [
        ViewModelFactoryModule::class,
        ReviewListViewModelModule::class
    ]
)
interface ReviewListComponent {
    fun inject(gifListFragment: ReviewListFragment)
}