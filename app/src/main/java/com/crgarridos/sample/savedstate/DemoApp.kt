package com.crgarridos.sample.savedstate

import com.crgarridos.sample.savedstate.injection.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class DemoApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        registerActivityLifecycleCallbacks(LogActivityLifecycleCallbacks())
    }
}