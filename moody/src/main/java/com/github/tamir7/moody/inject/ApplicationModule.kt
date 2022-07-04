package com.github.tamir7.moody.inject

import com.github.tamir7.moody.annotation.ApplicationScope
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module(subcomponents = [FragmentComponent::class])
class ApplicationModule {

    @Provides
    @ApplicationScope
    fun provideGson() = Gson()
}