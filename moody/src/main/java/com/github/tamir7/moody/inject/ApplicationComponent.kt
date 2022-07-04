package com.github.tamir7.moody.inject

import com.github.tamir7.moody.core.MoodyActivity
import com.github.tamir7.moody.core.MoodyApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun fragmentComponentBuilder() : FragmentComponent.Builder

    fun inject(application: MoodyApplication)
    fun inject(activity: MoodyActivity)
}