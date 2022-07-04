package com.github.tamir7.moody.inject

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [FragmentComponent::class])
class ApplicationModule {

    @Provides
    @Singleton
    fun provideGson() = Gson()
}