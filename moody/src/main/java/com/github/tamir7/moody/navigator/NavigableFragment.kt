package com.github.tamir7.moody.navigator

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment

open class NavigableFragment : Fragment() {
    private var abstractArguments: Arguments? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        arguments?.getString(ARGUMENTS)?.let { abstractArguments = Arguments.deserialize(it) }
    }

    fun setArguments(arguments: String) {
        val args = Bundle()
        args.putString(ARGUMENTS, arguments)
        setArguments(args)
    }

    protected fun <T : Arguments> getArguments(clazz: Class<T>): T {
        if (clazz.isInstance(abstractArguments)) {
            clazz.cast(abstractArguments)?.let { return it }
        }

        abstractArguments?.let {
            throw IllegalArgumentException("Incorrect arguments type. Type is ${it.type}")
        }

        throw IllegalStateException("Fragment does not contain arguments")
    }

    companion object {
        private const val ARGUMENTS = "arguments"
    }

}