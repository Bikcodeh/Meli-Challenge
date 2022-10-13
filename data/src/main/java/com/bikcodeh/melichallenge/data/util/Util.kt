package com.bikcodeh.melichallenge.data.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object Util {

    /***
     * Convert a generic class into a json string
     * @param item: any class to convert to json string
     * @return String: a kotlin object in string
     */
    inline fun <reified T> toJson(item: T): String {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(T::class.java).lenient()
        return jsonAdapter.toJson(item)
    }

    /**
     * Convert from a json string into a specific kotlin class
     * @param json: a kotlin class in json
     * @return T?: if the conversion is ok, return a specific class given
     * if not, returns null
     */
    inline fun <reified T> fromJson(json: String): T? {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(T::class.java).lenient()
        return try {
            jsonAdapter.fromJson(json)
        } catch (e: Exception) {
            null
        }
    }
}