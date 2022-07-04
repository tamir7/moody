package com.github.tamir7.moody.core

import com.github.tamir7.moody.fragment.HomeFragment
import com.github.tamir7.moody.navigator.Screen

class HomeScreen : Screen() {
    override fun create() = HomeFragment()
}