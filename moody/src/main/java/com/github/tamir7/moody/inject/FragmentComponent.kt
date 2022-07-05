package com.github.tamir7.moody.inject

import com.github.tamir7.moody.annotation.FragmentScope
import com.github.tamir7.moody.fragment.EvaluateFragment
import com.github.tamir7.moody.fragment.HomeFragment
import com.github.tamir7.moody.fragment.ResultFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {

    @Subcomponent.Builder
    interface Builder {
        fun fragmentModule(module: FragmentModule) : Builder
        fun build() : FragmentComponent
    }

    fun inject(fragment: HomeFragment)
    fun inject(fragment: EvaluateFragment)
    fun inject(fragment: ResultFragment)
}