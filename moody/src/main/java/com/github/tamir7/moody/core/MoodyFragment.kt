package com.github.tamir7.moody.core

import android.content.Context
import android.os.Bundle
import android.view.View
import butterknife.ButterKnife
import butterknife.Unbinder
import com.github.tamir7.moody.inject.FragmentComponent
import com.github.tamir7.moody.inject.FragmentModule
import com.github.tamir7.moody.navigator.NavigableFragment

abstract class MoodyFragment : NavigableFragment() {
    private lateinit var component: FragmentComponent
    private var unbinder: Unbinder? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component = MoodyApplication
            .getComponent(context)
            .fragmentComponentBuilder()
            .fragmentModule(FragmentModule())
            .build()

        inject(component)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unbinder = ButterKnife.bind(this, view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder?.unbind()
    }

    abstract fun inject(component: FragmentComponent)
}