package com.bikcodeh.melichallenge.data.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object Util {

    inline fun <reified T> toJson(item: T): String {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(T::class.java).lenient()
        return jsonAdapter.toJson(item)
    }

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