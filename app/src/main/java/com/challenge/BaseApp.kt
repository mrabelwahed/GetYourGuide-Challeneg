package com.challenge

import android.app.Application
import com.challenge.AppConst.BASE_URL
import com.challenge.di.component.AppComponent
import com.challenge.di.component.DaggerAppComponent
import com.challenge.di.module.NetworkModule
import com.challenge.di.module.RepositoryModule
import com.challenge.di.module.ReviewUsecaseModule

open class BaseApp : Application() {
    val appComponent: AppComponent by lazy { createAppComponent() }

    open fun createAppComponent(): AppComponent = DaggerAppComponent.builder()
        .networkModule(NetworkModule(BASE_URL))
        .repositoryModule(RepositoryModule())
        .reviewUsecaseModule(ReviewUsecaseModule())
        .build()

    override fun onCreate() {
        super.onCreate()
        createAppComponent().inject(this)
    }
}
