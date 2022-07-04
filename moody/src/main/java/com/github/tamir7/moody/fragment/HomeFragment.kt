package com.github.tamir7.moody.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.github.tamir7.moody.R
import com.github.tamir7.moody.core.MoodyFragment
import com.github.tamir7.moody.inject.FragmentComponent
import timber.log.Timber

class HomeFragment : MoodyFragment() {
    private lateinit var unbinder: Unbinder

    override fun inject(component: FragmentComponent) = component.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, null)
        unbinder = ButterKnife.bind(this, view)
        return  view
    }

    override fun onDestroyView() {
        unbinder.unbind()
        super.onDestroyView()
    }

    @OnClick(R.id.button_first)
    fun onClickButton() {
        Timber.d("Click!")
    }
}