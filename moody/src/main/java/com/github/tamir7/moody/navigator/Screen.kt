package com.github.tamir7.moody.navigator

import android.content.Intent
import com.github.tamir7.moody.util.RuntimeTypeAdapterFactory
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

abstract class Screen protected constructor() {
    private var abstractArguments: String? = null

    // used for Gson serialization
    @Suppress("unused")
    private val type: String = javaClass.simpleName

    protected constructor(arguments: Arguments) : this() {
        abstractArguments = arguments.serialize()
    }

    protected abstract fun create(): NavigableFragment

    fun createFragment(): NavigableFragment {
        val fragment = create()
        abstractArguments?.let { fragment.setArguments(it) }
        return fragment
    }

    fun serialize(): String {
        val gson = GsonBuilder().registerTypeAdapterFactory(factory).create()
        return gson.toJson(this)
    }

    companion object {
        private const val SCREENS = "screens"
        private val factory = RuntimeTypeAdapterFactory.of(Screen::class.java)
        private val gson = GsonBuilder().registerTypeAdapterFactory(factory).create()

        @JvmStatic
        fun <T : Screen> registerSubclass(clazz: Class<T>) {
            factory.registerSubtype(clazz)
        }

        fun <T : Screen> deserialize(json: String): T {
            return gson.fromJson(json, TypeToken.get(Screen::class.java).type)
        }

        fun <T : Screen> serialize(intent: Intent, screen: T) = serialize(intent, listOf(screen))

        fun <T : Screen> serialize(intent: Intent, screens: List<T>) {
            val serializedScreens = ArrayList<String>()
            screens.mapTo(serializedScreens) { it.serialize() }
            intent.putStringArrayListExtra(SCREENS, serializedScreens)
        }

        fun deserialize(intent: Intent): List<Screen>? {
            val serializedScreens = intent.getStringArrayListExtra(SCREENS)
            var screens: ArrayList<Screen>? = null
            if (serializedScreens != null) {
                screens = ArrayList()
                serializedScreens.mapTo(screens) { deserialize(it) }
            }

            return screens
        }
    }
}