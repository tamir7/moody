package com.github.tamir7.moody.core

import com.github.tamir7.moody.fragment.HomeFragment
import com.github.tamir7.moody.fragment.EvaluateFragment
import com.github.tamir7.moody.navigator.Arguments
import com.github.tamir7.moody.navigator.Screen

class HomeScreen: Screen() {
    override fun create() = HomeFragment()
}

class EvaluateArguments(val file: String): Arguments()

class EvaluateScreen(args: EvaluateArguments): Screen(args) {

    override fun create() = EvaluateFragment()
}

