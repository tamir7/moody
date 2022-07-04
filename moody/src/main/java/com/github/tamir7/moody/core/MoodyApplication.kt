package com.github.tamir7.moody.core

import android.app.Application
import android.content.Context
import com.github.tamir7.moody.inject.ApplicationComponent
import com.github.tamir7.moody.inject.DaggerApplicationComponent
import com.github.tamir7.moody.navigator.Screen
import dagger.internal.DaggerCollections
import timber.log.Timber

class MoodyApplication : Application() {
    private lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.create()
        component.inject(this)

        Timber.plant(Timber.DebugTree())

        registerScreens()
        registerArguments()
    }

    companion object {
        fun getComponent(context: Context) = (context.applicationContext as MoodyApplication).component
    }

    private fun registerScreens() {
        Screen.registerSubclass(HomeScreen::class.java)
    }

    private fun registerArguments() {}
}