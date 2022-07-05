package com.github.tamir7.moody.core

import com.github.tamir7.moody.fragment.HomeFragment
import com.github.tamir7.moody.fragment.EvaluateFragment
import com.github.tamir7.moody.fragment.ResultFragment
import com.github.tamir7.moody.model.Emotions
import com.github.tamir7.moody.navigator.Arguments
import com.github.tamir7.moody.navigator.Screen

class EvaluateArguments(val file: String): Arguments()
class ResultArguments(val emotions: Emotions?, var error: String?): Arguments()

class HomeScreen: Screen() {
    override fun create() = HomeFragment()
}

class EvaluateScreen(args: EvaluateArguments): Screen(args) {
    override fun create() = EvaluateFragment()
}

class ResultScreen(args: ResultArguments): Screen(args) {
    override fun create() = ResultFragment()
}
