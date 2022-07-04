package com.github.tamir7.moody.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.github.tamir7.moody.inject.FragmentComponent
import com.github.tamir7.moody.inject.FragmentModule
import com.github.tamir7.moody.navigator.NavigableFragment

abstract class MoodyFragment : NavigableFragment() {
    private lateinit var component: FragmentComponent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component = MoodyApplication
            .getComponent(context)
            .fragmentComponentBuilder()
            .fragmentModule(FragmentModule())
            .build()

        inject(component)
    }

    abstract fun inject(component: FragmentComponent)
}