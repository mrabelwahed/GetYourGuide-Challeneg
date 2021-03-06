package com.challenge.di.component

import com.challenge.BaseApp
import com.challenge.di.module.NetworkModule
import com.challenge.di.module.RepositoryModule
import com.challenge.di.module.ReviewUsecaseModule
import com.challenge.di.scope.AppScope
import com.challenge.di.subcomponent.ReviewListComponent
import dagger.Component

@AppScope
@Component(
    modules = [
        NetworkModule::class,
        ReviewUsecaseModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {
    fun newReviewLisComponent(): ReviewListComponent
    fun inject(app: BaseApp)
}
