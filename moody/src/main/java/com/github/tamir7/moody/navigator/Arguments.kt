package com.github.tamir7.moody.navigator


import com.github.tamir7.moody.util.RuntimeTypeAdapterFactory
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

abstract class Arguments {
    // used for Gson serialization
    internal var type: String? = null

    fun serialize(): String {
        type = javaClass.simpleName
        return gson.toJson(this)
    }

    companion object {
        private val factory = RuntimeTypeAdapterFactory.of(Arguments::class.java)
        private val gson = GsonBuilder().registerTypeAdapterFactory(factory).create()

        fun <T : Arguments> registerSubclass(clazz: Class<T>) {
            factory.registerSubtype(clazz)
        }

        fun <T : Arguments> deserialize(json: String): T {
            return gson.fromJson(json, TypeToken.get(Arguments::class.java).type)
        }
    }
}